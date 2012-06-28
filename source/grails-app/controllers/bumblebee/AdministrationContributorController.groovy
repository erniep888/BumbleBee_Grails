package bumblebee

import groovy.xml.MarkupBuilder

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
        if (contributor)
            contributor.delete(flush: true)
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
        }
        redirect(action: "list", params: [givenName: params.givenName])
    }

}
