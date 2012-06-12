<%--
  Created by IntelliJ IDEA.
  User: pascherk
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
    <div id="dashboardNav">
        <a href="/" class="selected">By Phase</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="/">By Project</a>
    </div>
    <div id="mock1">
        <img src="${resource(dir: 'images/mockups', file: 'Dashboard_Chart.png')}" alt="Dashboard"/>
    </div>
</body>
</html>