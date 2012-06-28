<%@ page import="bumblebee.Contributor" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<div class="listContainer">
    <table border="0">
        <tr>
            <td class="labelColumn vertAlignTop">Search Active Directory</td>
            <td class="editDetails caseTopPadding">
                <g:form name="searchActiveDirectory" action="list" >
                    <div class="fieldcontain ${hasErrors(bean: activeDirectoryUserInformationInstance, field: 'givenName', 'error')} ">
                        <label for="givenName" class="narrow">
                            <g:message code="activeDirectoryUserInformation.givenName.label" default="First Name"/>
                        </label>
                        <g:textField name="givenName" value="${activeDirectoryUserInformationInstance?.givenName}" size="20"/>
                    </div>
                    <div class="fieldcontain ${hasErrors(bean: activeDirectoryUserInformationInstance, field: 'lastName', 'error')} ">
                        <label for="lastName" class="narrow">
                            <g:message code="activeDirectoryUserInformation.lastName.label" default="Last Name"/>
                        </label>
                        <g:textField name="lastName" value="${activeDirectoryUserInformationInstance?.lastName}" size="20"/>
                    </div>
                    <div class="fieldcontain ${hasErrors(bean: activeDirectoryUserInformationInstance, field: 'userPrincipalName', 'error')} ">
                        <label for="userPrincipalName" class="narrow">
                            <g:message code="activeDirectoryUserInformation.userPrincipalName.label" default="Username"/>
                        </label>
                        <g:textField name="userPrincipalName" value="${activeDirectoryUserInformationInstance?.userPrincipalName}" size="20"/>
                    </div>
                    <input value="Search" type="submit" class="vertMarginTen"/>
                </g:form>
            </td>
        </tr>
        <tr>
            <td class="labelColumn vertAlignTop">Results</td>
            <td>
                <div class="usernameList usernameSearchResults">
                    <table class="listTable">
                        <thead class="listHeader">
                        <tr>
                            <td class="fileListPadding">Name</td>
                            <td class="center">Action</td>
                        </tr>
                        </thead>
                        <g:each var="activeDirectoryUser" in="${activeDirectoryUsers}">
                            <tr>
                                <td class="fileListPadding linkNameColumn">${activeDirectoryUser?.givenName + " " + activeDirectoryUser?.lastName}</td>
                                <td class="center actionColumn">
                                    <g:if test="${activeDirectoryUser.allowAddToSystem}">
                                        <g:link action="add" params="[userPrincipalName: activeDirectoryUser.userPrincipalName,
                                            givenName: activeDirectoryUser.givenName, lastName: activeDirectoryUser.lastName,
                                            emailAddress: activeDirectoryUser.emailAddress]">Add</g:link>
                                    </g:if>
                                    <g:else>
                                         Added
                                    </g:else>
                                </td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</div>
