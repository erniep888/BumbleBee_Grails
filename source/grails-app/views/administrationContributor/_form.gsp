<%@ page import="bumblebee.Contributor" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<g:include view="/administrationActiveDirectory/_form.gsp"
           model="${[activeDirectoryUserInformationInstance:activeDirectoryUserInformationInstance ,
                   activeDirectoryUsers: activeDirectoryUsers]}" />
<div class="listContainer">
    <table border="0">
        <tr>
            <td class="labelColumn vertAlignTop">Contributors</td>
            <td>
                <div class="usernameList">
                    <table class="listTable">
                        <thead class="listHeader">
                        <tr>
                            <td class="fileListPadding">Contributor</td>
                            <td class="center">Action</td>
                        </tr>
                        </thead>
                        <g:each var="contributor" in="${contributors}">
                            <tr>
                                <td class="fileListPadding linkNameColumn">${contributor.fullName}</td>
                                <td class="center actionColumn">
                                    <g:link action="delete" params="[id: contributor.id]">Delete</g:link>
                                </td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</div>
