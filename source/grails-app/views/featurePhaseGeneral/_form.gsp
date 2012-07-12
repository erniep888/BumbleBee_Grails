<%@ page import="bumblebee.Contributor; bumblebee.Contributor; bumblebee.FeaturePhaseStatus; java.text.SimpleDateFormat; bumblebee.Feature" %>
<%@ page import="bumblebee.FeaturePhase" %>

<%
    def simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy")
%>

<g:form action="save">
    <fieldset class="form">

        <div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'developer', 'error')} ">
            <label for="developer">
                <g:message code="featurePhase.developer.label" default="Developer"/>
            </label>
            <g:select name="developer" value="${featurePhaseInstance?.developer?.username}"
                      from="${Contributor.findAll().sort()}"
                      optionKey="username"
                      noSelection="${[null:'Select One...']}" />
        </div>

        <div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'tester', 'error')} ">
            <label for="tester">
                <g:message code="featurePhase.tester.label" default="Tester"/>
            </label>
            <g:select name="tester" value="${featurePhaseInstance?.tester?.username}"
                      from="${Contributor.findAll().sort()}"
                      optionKey="username"
                      noSelection="${[null:'Select One...']}" />
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
                <g:message code="featurePhaseCase.status.label" default="Status"/>
            </label>
            <g:select name="status" value="${featurePhaseInstance?.status}"
                      from="${FeaturePhaseStatus.findAll().sort()}"
                      noSelection="${['[a] not started':'Select One...']}"/>
        </div>

        <div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'executionDate', 'error')} ">
            <label for="executionDate">
                <g:message code="featurePhase.executionDate.label" default="Completed"/>
            </label>
            ${(featurePhaseInstance?.executionDate) ? simpleDateFormat.format(featurePhaseInstance?.executionDate) : 'not complete'}
        </div>

        <div class="fieldcontain ${hasErrors(bean: featurePhaseInstance, field: 'comments', 'error')} ">
            <label for="comments">
                <g:message code="featurePhase.comments.label" default="Comments"/>
            </label>
            <g:textArea name="comments" value="${featurePhaseInstance?.comments}" cols="1" rows="1"/>
        </div>
        <g:hiddenField name="id" value="${params.id}"/>
        <g:hiddenField name="featureId" value="${params.featureId}"/>
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="save" class="save"
                        value="${message(code: 'default.button.save.label', default: 'Save')}"/>
    </fieldset>
</g:form>