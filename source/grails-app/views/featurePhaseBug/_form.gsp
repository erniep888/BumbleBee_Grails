<%@ page import="bumblebee.FeaturePhaseBug; bumblebee.BugSystemSettings; bumblebee.Vendor; bumblebee.FeaturePhaseCaseStatus; bumblebee.FeaturePhase" %>
<%@ page import="bumblebee.FeaturePhaseCase" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<div class="listContainer">
    <table class="thirdPartyCases" border="0">
        <tr>
            <td class="labelColumn vertAlignTop">Bugs</td>
            <td class="caseList">
                <div class="linkList">
                    <table class="listTable">
                        <thead class="listHeader">
                            <tr>
                                <td class="center caseVendor">Bug Id</td>
                                <td class="center">Priority</td>
                                <td class="center">Status</td>
                                <td class="center">Severity</td>
                                <td class="center">Summary</td>
                                <td class="center">Actions</td>
                            </tr>
                        </thead>
                        <%
                            BugSystemSettings bugSystemSettings = BugSystemSettings.findBySystemName("Mantis")
                        %>
                        <g:each var="mantisBug" in="${mantisBugs}">
                            <%
                                def summary = mantisBug?.summary
                                if (summary?.length() > 30){
                                    summary = summary?.substring(0,30) + "..."
                                }
                                def currentFeaturePhaseBug = FeaturePhaseBug.findByBugSystemId(mantisBug?.id)
                            %>
                            <tr>
                                <td class="center"><a href="${bugSystemSettings?.bugAccessUrl + mantisBug.id}" target="_blank">${mantisBug.id}</a></td>
                                <td class="center ">${mantisBug.getPriorityAsString()}</td>
                                <td class="center">${mantisBug.getStatusAsString()}</td>
                                <td class="center">${mantisBug.getSeverityAsString()}</td>
                                <td class="center">${summary}</td>
                                <td class="center actionColumn">
                                    <g:link action="editBug" params="[featureId: params.featureId, id:params.id, bugId:currentFeaturePhaseBug?.id]">Edit</g:link>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <g:link action="deleteBug" params="[featureId: params.featureId, id:params.id, bugId:currentFeaturePhaseBug?.id]">Delete</g:link>
                                </td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td class="labelColumn vertAlignTop">Details</td>
            <td class="editDetails caseTopPadding">
                <g:form name="editBugs" action="save" controller="featurePhaseBug" >
                    <g:hiddenField name="id" value="${params.id}" />
                    <g:hiddenField name="featureId" value="${params.featureId}" />
                    <g:hiddenField name="bugId" value="${featurePhaseBugInstance?.id}" />
                    <div class="fieldcontain ${hasErrors(bean: bugInstance, field: 'bugSystemId', 'error')} ">
                        <label for="bugSystemId" class="narrow">
                            <g:message code="featurePhaseBug.bugSystemId.label" default="Bug Id"/>
                        </label>
                        <g:textField name="bugSystemId" value="${bugInstance?.bugSystemId}" size="6"/>
                    </div>
                    <g:if test="${bugInformationInstance}">
                        <div class="fieldcontain ${hasErrors(bean: bugInformationInstance, field: 'priority', 'error')} ">
                            <label for="priority">
                                <g:message code="mantisBugInformation.priority.label" default="Priority"/>
                            </label>
                            ${bugInformationInstance?.priority}
                        </div>
                        <div class="fieldcontain ${hasErrors(bean: bugInformationInstance, field: 'status', 'error')} ">
                            <label for="status">
                                <g:message code="mantisBugInformation.status.label" default="Status"/>
                            </label>
                            ${bugInformationInstance?.getStatusAsString()}
                        </div>
                        <div class="fieldcontain ${hasErrors(bean: bugInformationInstance, field: 'severity', 'error')} ">
                            <label for="severity">
                                <g:message code="mantisBugInformation.severity.label" default="Severity"/>
                            </label>
                            ${bugInformationInstance?.getSeverityAsString()}
                        </div>
                        <div class="fieldcontain ${hasErrors(bean: bugInformationInstance, field: 'summary', 'error')} ">
                            <label for="summary">
                                <g:message code="mantisBugInformation.summary.label" default="Summary"/>
                            </label>
                            ${bugInformationInstance?.summary}
                        </div>
                        <input value="Save" type="submit" class="vertMarginTen"/>
                    </g:if>
                    <g:else>
                        <input value="Add" type="submit" class="vertMarginTen"/>
                    </g:else>
                </g:form>
            </td>
        </tr>
    </table>
</div>
