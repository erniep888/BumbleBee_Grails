<%@ page import="bumblebee.Feature" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<div class="listContainer">
    <table border="0">
        <tr>
            <td class="labelColumn vertAlignTop">Deleted Objects</td>
            <td>
                <div class="usernameList">
                    <table class="listTable">
                        <thead class="listHeader">
                        <tr>
                            <td class="fileListPadding">Name</td>
                            <td class="fileListPadding">Description</td>
                            <td class="center">Action</td>
                        </tr>
                        </thead>
                        <g:each var="feature" in="${features}">
                            <tr>
                                <td class="fileListPadding linkNameColumn">${feature.name}</td>
                                <td class="fileListPadding linkNameColumn">${feature.description}</td>
                                <td class="center actionColumn">
                                    <g:link action="recover" params="[id: feature.id]">Recover</g:link>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <g:link action="delete" params="[id: feature.id]">Delete</g:link>
                                </td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</div>
