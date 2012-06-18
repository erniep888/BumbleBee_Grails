<%@ page import="bumblebee.Feature" %>
<%@ page import="bumblebee.FeaturePhase" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="featurePhaseEdit">
    <g:render template="/shared/pageTitle" model="${ [pageTitle: "Edit " + message(code: 'feature.label', default: 'Feature') + " Phase General"] }"/>
</head>
</html>