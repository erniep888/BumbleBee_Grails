<%@ page import="bumblebee.Feature" %>
<head>
    <meta name="layout" content="main">
    <g:render template="/shared/pageTitle" model="${ [pageTitle: "Create " + message(code: 'feature.label', default: 'Feature')] }"/>
</head>

<body>
<a href="#create-feature" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[message(code: 'feature.label', default: 'Feature')]"/></g:link></li>
    </ul>
</div>

<div id="create-feature" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[message(code: 'feature.label', default: 'Feature')]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${featureInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${featureInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="save">
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="create" class="save"
                            value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        </fieldset>
    </g:form>
</div>

</body>