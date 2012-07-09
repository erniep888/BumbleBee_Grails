<%@ page import="bumblebee.Project" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">

    <g:render template="/shared/pageTitle" model="${ [pageTitle: "Library"] }"/>
</head>

<body>
<a href="#edit-library" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
</div>
<g:render template="form"/>
</body>
</html>
