<%--
  Created by IntelliJ IDEA.
  bumblebee.Contributor: pascherk
  Date: 6/6/12
  Time: 5:09 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:meta name="app.userfriendly.applicationAcronym"/> - Dashboard</title>
</head>

<body>
<div class="nav" role="navigation">
    %{--<ul>--}%
        %{--<li>--}%
            %{--<g:link action="byPhase">--}%
                %{--<g:message code="dashboard.menu.byPhase.label"/>--}%
            %{--</g:link>--}%
        %{--</li>--}%
        %{--<li>--}%
            %{--<g:link action="byProject">--}%
                %{--<g:message code="dashboard.menu.byCategory.label" args="[message(code: 'feature.category.label', default: 'Project')]"/>--}%
            %{--</g:link>--}%
        %{--</li>--}%
    %{--</ul>--}%
</div>
<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>


<div id="mock1">
    <img src="${resource(dir: 'images/mockups', file: 'Dashboard_Chart.png')}" alt="Dashboard"/>
</div>
</body>
</html>