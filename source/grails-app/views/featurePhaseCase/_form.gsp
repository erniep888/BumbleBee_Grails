<%@ page import="bumblebee.Vendor; bumblebee.FeaturePhaseCaseStatus; bumblebee.FeaturePhase" %>
<%@ page import="bumblebee.FeaturePhaseCase" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<div class="listContainer">
    <table class="thirdPartyCases" border="0">
        <tr>
            <td class="labelColumn vertAlignTop">Third Party Cases</td>
            <td class="caseList">
                <div class="linkList">
                    <table class="listTable">
                        <thead class="listHeader">
                            <tr>
                                <td class="center caseVendor">Vendor</td>
                                <td class="center">Case Id</td>
                                <td class="center">Status</td>
                                <td class="center">Last Updated</td>
                                <td class="center">Actions</td>
                            </tr>
                        </thead>
                        <g:each var="featurePhaseCase" in="${featurePhaseInstance?.cases}">
                            <%
                                String caseId = featurePhaseCase.caseIdentifier
                                if (featurePhaseCase.href) {
                                    caseId = '<a href="' + featurePhaseCase.href + '" target="_blank">' + caseId + '</a>'
                                }
                            %>
                            <tr>
                                <td class="center">${featurePhaseCase.vendor}</td>
                                <td class="center ">${caseId}</td>
                                <td class="center">${featurePhaseCase.status}</td>
                                <td class="center caseLastUpdated dateColumn"><g:formatDate format="MM/dd/yyyy h:mm a" date="${featurePhaseCase?.lastUpdated}"/></td>
                                <td class="center actionColumn">
                                    <g:link action="editCase" params="[featureId: params.featureId, id:params.id, caseId:featurePhaseCase.id]">Edit</g:link>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <g:link action="deleteCase" params="[featureId: params.featureId, id:params.id, caseId:featurePhaseCase.id]">Delete</g:link>
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
                <g:form name="editCases" action="save" controller="featurePhaseCase" >
                    <g:hiddenField name="id" value="${params.id}" />
                    <g:hiddenField name="featureId" value="${params.featureId}" />
                    <g:hiddenField name="caseId" value="${caseInstance?.id}" />
                    <div class="fieldcontain ${hasErrors(bean: caseInstance, field: 'vendor', 'error')} ">
                        <label for="caseInstance.vendor" class="narrow">
                            <g:message code="featurePhaseCase.vendor.label" default="Vendor"/>
                        </label>
                        <g:select class="left" name="caseInstance.vendor" value="${caseInstance?.vendor}"
                                  from="${Vendor.findAll().sort()}"
                                  noSelection="${['unknown':'Select One...']}"/>
                    </div>
                    <div class="fieldcontain ${hasErrors(bean: caseInstance, field: 'status', 'error')} ">
                        <label for="caseInstance.status" class="narrow">
                            <g:message code="featurePhaseCase.status.label" default="Status"/>
                        </label>
                        <g:select name="caseInstance.status" value="${caseInstance?.status}"
                                  from="${FeaturePhaseCaseStatus.findAll().sort()}"
                                  noSelection="${['unknown':'Select One...']}"/>
                    </div>
                    <div class="fieldcontain ${hasErrors(bean: caseInstance, field: 'caseIdentifier', 'error')} ">
                        <label for="caseIdentifier" class="narrow">
                            <g:message code="featurePhaseCase.caseIdentifier.label" default="Id"/>
                        </label>
                        <g:textField name="caseIdentifier" value="${caseInstance?.caseIdentifier}" size="25"/>
                    </div>
                    <div class="fieldcontain ${hasErrors(bean: caseInstance, field: 'caseIdentifier', 'error')} ">
                        <label for="href" class="narrow">
                            <g:message code="featurePhaseCase.href.label" default="Id"/>
                        </label>
                        <g:textField name="href" value="${caseInstance?.href}" size="40"/>
                    </div>
                    <input value="Save" type="submit" class="vertMarginTen"/>
                </g:form>
            </td>
        </tr>
    </table>
</div>
