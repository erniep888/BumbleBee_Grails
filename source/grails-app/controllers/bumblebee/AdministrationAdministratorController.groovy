package bumblebee

// TODO: This needs to be reworked since this was added a result of a late requirements change.
class AdministrationAdministratorController {
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
                def existingUser = Administrator.findByUsername(user.userPrincipalName)
                if (existingUser)
                    user.allowAddToSystem = false
            }
        }

        model: [activeDirectoryUserInformationInstance: activeDirectoryUserInformation,
                activeDirectoryUsers: users,
                administrators: Administrator.findAll().sort {m -> m.fullName}]
    }

    def delete(long id){
        def administrator = Administrator.findById(id)
        if (administrator)
            administrator.delete(flush: true)
        redirect(action: "list", params: params)
    }

    def add(){
        def administrator = new Administrator(fullName: params.givenName + " " + params.lastName,
                username: params.userPrincipalName, emailAddress: params.emailAddress)
        administrator.validate()
        if (administrator.hasErrors()){
            flash.message = administrator.errors.getAllErrors().first().toString()
            redirect(action: "list", params: params)
            return
        } else {
            administrator.save(flush: true)
        }
        redirect(action: "list", params: [givenName: params.givenName])
    }
}
