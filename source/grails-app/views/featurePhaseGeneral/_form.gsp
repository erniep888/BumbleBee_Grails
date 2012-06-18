<%@ page import="java.text.SimpleDateFormat; bumblebee.Feature" %>
<%@ page import="bumblebee.FeaturePhase" %>
<%@ page import="bumblebee.FeatureStatusMap" %>

<%
    def simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy")
%>

<div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'developer', 'error')} ">
    <label for="developer">
        <g:message code="featurePhase.developer.label" default="Developer"/>
    </label>
    <g:textField name="developer" value="${featurePhaseInstance?.developer}" size="40"/>
</div>

<div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'tester', 'error')} ">
    <label for="tester">
        <g:message code="featurePhase.tester.label" default="Tester"/>
    </label>
    <g:textField name="tester" value="${featurePhaseInstance?.tester}" size="40"/>
</div>

<div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'developmentWorkEffort', 'error')} ">
    <label for="developmentWorkEffort">
        <g:message code="featurePhase.developmentWorkEffort.label" default="Development Work Effort"/>
    </label>
    <g:textField name="developmentWorkEffort" value="${featurePhaseInstance?.developmentWorkEffort}" size="3"/>
</div>

<div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'testWorkEffort', 'error')} ">
    <label for="testWorkEffort">
        <g:message code="featurePhase.testWorkEffort.label" default="Test Work Effort"/>
    </label>
    <g:textField name="testWorkEffort" value="${featurePhaseInstance?.testWorkEffort}" size="3"/>
</div>

<div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'status', 'error')} ">
    <label for="status">
        <g:message code="featurePhase.status.label" default="Status"/>
    </label>
    <g:select name="status" value="${featurePhaseInstance?.status}"
        from="${FeatureStatusMap.getAllStatusStrings()}"
        noSelection="['':'not started']" />
</div>

<div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'executionDate', 'error')} ">
    <label for="executionDate">
        <g:message code="featurePhase.executionDate.label" default="Completed"/>
    </label>
    ${(featurePhaseInstance?.executionDate)?simpleDateFormat.format(featurePhaseInstance?.executionDate):'not complete'}
</div>

<div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'comments', 'error')} ">
    <label for="comments">
        <g:message code="featurePhase.comments.label" default="Comments"/>
    </label>
    <g:textArea name="comments" value="${featurePhaseInstance?.comments}" cols="1" rows="1" />
</div>
<g:hiddenField name="id" value="${params.id}" />
<g:hiddenField name="featureId" value="${params.featureId}" />