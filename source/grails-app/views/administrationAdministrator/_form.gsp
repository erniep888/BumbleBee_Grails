<%@ page import="bumblebee.Administrator" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<g:include view="/administrationActiveDirectory/_form.gsp"
           model="${[activeDirectoryUserInformationInstance:activeDirectoryUserInformationInstance ,
                   activeDirectoryUsers: activeDirectoryUsers]}" />
<div class="listContainer">
    <table border="0">
        <tr>
            <td class="labelColumn vertAlignTop">Administrators</td>
            <td>
                <div class="usernameList">
                    <table class="listTable">
                        <thead class="listHeader">
                        <tr>
                            <td class="fileListPadding">Administrator</td>
                            <td class="center">Action</td>
                        </tr>
                        </thead>
                        <g:each var="administrator" in="${administrators}">
                            <tr>
                                <td class="fileListPadding linkNameColumn">${administrator.fullName}</td>
                                <td class="center actionColumn">
                                    <g:link action="delete" params="[id: administrator.id]">Delete</g:link>
                                </td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</div>
