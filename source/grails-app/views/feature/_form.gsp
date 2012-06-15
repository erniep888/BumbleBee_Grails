<%@ page import="bumblebee.Feature" %>

<div class="fieldcontain ${hasErrors(bean: featureInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="feature.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${featureInstance?.name}" size="40"/>
</div>

<div class="fieldcontain ${hasErrors(bean: featureInstance, field: 'description', 'error')} ">
    <label for="description">
        <g:message code="feature.description.label" default="Description"/>

    </label>
    <g:textField name="description" value="${featureInstance?.description}" size="40"/>
</div>

<div class="fieldcontain ${hasErrors(bean: featureInstance, field: 'category', 'error')} ">
    <label for="category">
        <g:message code="feature.category.label" default="Category"/>

    </label>
    <g:textField name="category" value="${featureInstance?.category}" size="40"/>
</div>
<g:hiddenField name="isDeleted" value="false"/>

