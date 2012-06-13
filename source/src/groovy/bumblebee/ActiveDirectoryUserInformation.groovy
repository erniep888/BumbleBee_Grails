package bumblebee

import javax.naming.directory.Attribute
import javax.naming.directory.Attributes
import javax.naming.directory.SearchResult

/**
 * Created by IntelliJ IDEA.
 * User: pascherk
 * Date: Nov 25, 2008
 * Time: 2:36:56 PM
 * To change this template use File | Settings | File Templates.
 */

class ActiveDirectoryUserInformation {
    String employeeNumber
    String givenName
    String middleInitial
    String lastName
    String displayName
    String description
    String department
    String title
    String userPrincipalName
    String telephoneNumber
    String emailAddress
    String mailNickName
    boolean successfulLoad

    static ActiveDirectoryUserInformation fromSearchResult(SearchResult searchResult) {
        ActiveDirectoryUserInformation activeDirectoryUserInformation = new ActiveDirectoryUserInformation()
        activeDirectoryUserInformation.successfulLoad = true

        try {
            Attributes attrs = searchResult.getAttributes()
            Attribute displayNameAttr = attrs.get("displayName")
            activeDirectoryUserInformation.setDisplayName(displayNameAttr.get().toString())
            Attribute employeeNumberAttr = attrs.get("employeeNumber")
            // TODO: EmployeeNumber doesn't exist for consultants
            activeDirectoryUserInformation.setEmployeeNumber((employeeNumberAttr != null) ? employeeNumberAttr.get().toString() : "UNDEFINED")
            Attribute givenNameAttr = attrs.get("givenName")
            activeDirectoryUserInformation.setGivenName((givenNameAttr != null) ? givenNameAttr.get().toString() : null)
            Attribute descriptionAttr = attrs.get("description")
            activeDirectoryUserInformation.setDescription((descriptionAttr != null) ? descriptionAttr.get().toString() : null)
            Attribute departmentAttr = attrs.get("department")
            activeDirectoryUserInformation.setDepartment((departmentAttr != null) ? departmentAttr.get().toString() : null)
            Attribute titleAttr = attrs.get("title")
            activeDirectoryUserInformation.setTitle((titleAttr != null) ? titleAttr.get().toString() : null)
            Attribute userPrincipalNameAttr = attrs.get("sAMAccountName")
            activeDirectoryUserInformation.setUserPrincipalName(userPrincipalNameAttr.get().toString())
            Attribute telephoneNumberAttr = attrs.get("telephoneNumber")
            activeDirectoryUserInformation.setTelephoneNumber((telephoneNumberAttr != null) ? telephoneNumberAttr.get().toString() : null)
            Attribute emailAddressAttr = attrs.get("mail")
            activeDirectoryUserInformation.setEmailAddress(emailAddressAttr.get().toString())
            Attribute mailNicknameAttr = attrs.get("mailNickname")
            activeDirectoryUserInformation.setMailNickName(mailNicknameAttr.get().toString())
            Attribute snAttr = attrs.get("sn")
            activeDirectoryUserInformation.setLastName(snAttr.get().toString())
            Attribute initials = attrs.get("initials")
            activeDirectoryUserInformation.setMiddleInitial((initials != null) ? initials.get().toString() : null)
        } catch (Exception ex) {
            activeDirectoryUserInformation.successfulLoad = false
        }
        return activeDirectoryUserInformation
    }
}