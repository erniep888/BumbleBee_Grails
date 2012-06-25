<%@ page import="bumblebee.FeaturePhase" %>
<%@ page import="bumblebee.Link" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<div class="listContainer">
    <table border="0">
        <tr>
            <td class="labelColumn vertAlignTop">Links</td>
            <td>
                <div class="linkList">
                    <table class="listTable">
                        <thead class="listHeader">
                        <tr>
                            <td class="fileListPadding">Name</td>
                            <td class="center">Last Updated</td>
                            <td class="center">Actions</td>
                        </tr>
                        </thead>
                        <g:each var="link" in="${featurePhaseInstance?.links}">
                            <%
                                String target = ""
                                if (link.inNewWindow) {
                                    target = 'target = "_blank"'
                                }
                            %>
                            <tr>
                                <td class="fileListPadding linkNameColumn"><a href="${link.href}" ${target}>${link.name}</a></td>
                                <td class="center dateColumn"><g:formatDate format="MM/dd/yyyy h:mm a" date="${link?.lastUpdated}"/></td>
                                <td class="center actionColumn">
                                    <g:link action="editLink" params="[featureId: params.featureId, id:params.id, linkId:link.id]">Edit</g:link>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <g:link action="deleteLink" params="[featureId: params.featureId, id:params.id, linkId:link.id]">Delete</g:link>
                                </td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td class="vertAlignTop">Details</td>
            <td class="editDetails">
                <g:form name="editLinks" action="save" controller="featurePhaseLink" >
                    <g:hiddenField name="id" value="${params.id}" />
                    <g:hiddenField name="featureId" value="${params.featureId}" />
                    <g:hiddenField name="linkId" value="${linkInstance?.id}" />
                    <div class="linkEdit ${hasErrors(bean: linkInstance, field: 'name', 'error')} ">
                        <label for="name">
                            <g:message code="link.name.label" default="Name"/>
                        </label>
                        <g:textField name="name" value="${linkInstance?.name}" size="40"/>
                    </div>
                    <div class="linkEdit ${hasErrors(bean: linkInstance, field: 'href', 'error')} ">
                        <label for="href">
                            <g:message code="link.href.label" default="URL"/>
                        </label>
                        <g:textField name="href" value="${linkInstance?.href}" size="40"/>
                    </div>
                    <div class="linkEdit ${hasErrors(bean: linkInstance, field: 'inNewWindow', 'error')} ">
                        <g:checkBox name="inNewWindow" value="${linkInstance?.inNewWindow}" size="40"/>
                        <g:message code="link.inNewWindow.label" default="Open new window?"/>

                    </div>
                    <input value="Save" type="submit"/>
                </g:form>
            </td>
        </tr>
    </table>
</div>
