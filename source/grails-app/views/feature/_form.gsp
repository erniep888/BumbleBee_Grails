<%@ page import="bumblebee.Feature" %>



<div class="fieldcontain ${hasErrors(bean: featureInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="feature.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${featureInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: featureInstance, field: 'description', 'error')} ">
    <label for="description">
        <g:message code="feature.description.label" default="Description"/>

    </label>
    <g:textField name="description" value="${featureInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: featureInstance, field: 'featurePhases', 'error')} ">
    <label for="featurePhases">
        <g:message code="feature.featurePhases.label" default="Feature Phases"/>

    </label>
    <g:select name="featurePhases" from="${bumblebee.FeaturePhase.list()}" multiple="multiple" optionKey="id" size="5"
              value="${featureInstance?.featurePhases*.id}" class="many-to-many"/>
</div>

