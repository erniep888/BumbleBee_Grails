<%@ page import="bumblebee.Feature" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <g:render template="/shared/pageTitle" model="${ [pageTitle: "Edit " + message(code: 'feature.label', default: 'Feature') + " Phase General"] }"/>

</head>
<body>
<a href="#create-feature" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[message(code: 'feature.label', default: 'Feature')]"/></g:link></li>
    </ul>
</div>

<div class="content" role="main">
    <g:render template="readOnlyFeatureInformation"/>

    <g:render template="/shared/phaseMenu"/>

    <g:render template="/shared/featurePhaseMenu"/>

    <g:form action="save">
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="save" class="save"
                            value="${message(code: 'default.button.save.label', default: 'Save')}"/>
        </fieldset>
    </g:form>
</div>

</body>
</html>