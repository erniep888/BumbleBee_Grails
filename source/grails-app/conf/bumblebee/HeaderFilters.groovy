package bumblebee

class HeaderFilters {
    def authenticationService
    def filters = {
        all(controller: '*', action: '*') {
            before = {
                def processAction = true
                if (!session.currentUser){
                    def user = params.uid
                    if (user){
                        if (user.contains("MARTINMARIETTA\\"))
                            user = user.substring("MARTINMARIETTA\\".length())

                        def contributor = Contributor.findByUsername(user)
                        if (!contributor){
                            def nonContributor = NonContributor.findByUsername(user)
                            if (!nonContributor){
                                ActiveDirectoryUserInformation activeDirectoryUserInformation = activeDirectoryService.retrieveUserInformation(user)
                                if (activeDirectoryUserInformation) {
                                    nonContributor = new NonContributor(fullName: activeDirectoryUserInformation.givenName + " " + activeDirectoryUserInformation.lastName,
                                            username: activeDirectoryUserInformation.userPrincipalName, emailAddress: activeDirectoryUserInformation.emailAddress)
                                    nonContributor.save(flush: true)
                                    AuditActivity auditActivity = new AuditActivity(type: "user", description: "New non-contributor, {" +
                                            nonContributor.username + "}, added by virtue of system access.")
                                    auditActivity.save(flush: true)
                                    user = activeDirectoryUserInformation.givenName + " " + activeDirectoryUserInformation.lastName
                                }
                            } else
                                user = nonContributor.fullName
                        } else
                            user = contributor.fullName

                        AuditActivity auditActivity = new AuditActivity(type: "user", description: "User login: {" + user + "}.")
                        auditActivity.save(flush: true)
                        session.currentUser = user
                    } else {
                        authenticationService.authenticate(request, response)
                        processAction = false
                    }
                    return processAction
                }
            }
            after = { Map model ->
                model?.currentProject = Project.findById(1)
            }
            afterView = { Exception e ->

            }
        }
    }
}
