package bumblebee

class AdministrationContributorController {

    static defaultAction = "list"

    def activeDirectoryService

    def list() {

        String lastName = ''
        String firstName = ''
        String username = ''
        def users
        if (params.lastName && params.lastName?.length() > 1)
            lastName = params.lastName
        if (params.givenName && params.givenName?.length() > 1)
            firstName = params.givenName
        if (params.userPrincipalName && params.userPrincipalName?.length() > 1)
            username = params.userPrincipalName
        if ((lastName + firstName + username).trim().length() > 1) {
            users = activeDirectoryService.findUsers(username, firstName, lastName)
        }

        ActiveDirectoryUserInformation activeDirectoryUserInformation =
            new ActiveDirectoryUserInformation(givenName: firstName, lastName: lastName, userPrincipalName: username)
        for (user in users){
            if (user.userPrincipalName){
                def existingUser = Contributor.findByUsername(user.userPrincipalName)
                if (existingUser)
                    user.allowAddToSystem = false
            }
        }

        model: [activeDirectoryUserInformationInstance: activeDirectoryUserInformation,
                activeDirectoryUsers: users,
                contributors: Contributor.findAll().sort {m -> m.fullName}]
    }

    def delete(long id){
        def contributor = Contributor.findById(id)
        if (contributor){
            def message = ''
            try{
                contributor.delete(flush: true)
            } catch (Exception ex){
                flash.message = ex.message
                redirect(action: "list", params: params)
                return
            }
            AuditActivity auditActivity = new AuditActivity(type: "user", description: "Contributor, {" +
                    contributor.username + "}, deleted by user, {" + request.remoteUser + "}.")
            auditActivity.save(flush: true)
        }
        redirect(action: "list", params: params)
    }

    def add(){
        def contributor = new Contributor(fullName: params.givenName + " " + params.lastName,
                username: params.userPrincipalName, emailAddress: params.emailAddress)
        contributor.validate()
        if (contributor.hasErrors()){
            flash.message = contributor.errors.getAllErrors().first().toString()
            redirect(action: "list", params: params)
            return
        } else {
            contributor.save(flush: true)
            AuditActivity auditActivity = new AuditActivity(type: "user", description: "New contributor, {" +
                contributor.username + "}, added by user, {" + request.remoteUser + "}.")
            auditActivity.save(flush: true)
            def nonContributor = NonContributor.findByUsername(contributor.username)
            if (nonContributor){
                nonContributor.delete()

                auditActivity = null
                auditActivity = new AuditActivity(type: "user", description: "Non-contributor, {" +
                        nonContributor.username + "}, deleted because they were made a contributor by " +
                        "user, {" + request.remoteUser + "}.")
                auditActivity.save(flush: true)
            }
        }
        redirect(action: "list", params: [givenName: params.givenName])
    }


    /***************** Partial View Actions Below ********************/

    def getUsernameOrFullName(){
//        
//				def user = request.remoteUser

//        if (!session.currentUser){
//            if (user.contains("MARTINMARIETTA\\"))
//                user = user.substring("MARTINMARIETTA\\".length())
//
//            def contributor = Contributor.findByUsername(user)
//            if (!contributor){
//                def nonContributor = NonContributor.findByUsername(user)
//                if (!nonContributor){
//                    ActiveDirectoryUserInformation activeDirectoryUserInformation = activeDirectoryService.retrieveUserInformation(user)
//                    if (activeDirectoryUserInformation) {
//                        nonContributor = new NonContributor(fullName: activeDirectoryUserInformation.givenName + " " + activeDirectoryUserInformation.lastName,
//                                username: activeDirectoryUserInformation.userPrincipalName, emailAddress: activeDirectoryUserInformation.emailAddress)
//                        nonContributor.save(flush: true)
//                        AuditActivity auditActivity = new AuditActivity(type: "user", description: "New non-contributor, {" +
//                                nonContributor.username + "}, added by virtue of system access.")
//                        auditActivity.save(flush: true)
//                        user = activeDirectoryUserInformation.givenName + " " + activeDirectoryUserInformation.lastName
//                    }
//                } else
//                    user = nonContributor.fullName
//            } else
//                user = contributor.fullName
//
//            AuditActivity auditActivity = new AuditActivity(type: "user", description: "User login: {" + user + "}.")
//            auditActivity.save(flush: true)
//            session.currentUser = user
//        }  else
//            user = session.currentUser

        render 'Bob'
    }
}
