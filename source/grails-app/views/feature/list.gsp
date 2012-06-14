<%@ page import="bumblebee.Feature" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="featureLabel" value="${message(code: 'feature.label', default: 'Feature')}"/>
    <title><g:message code="app.userfriendly.applicationAcronym" default="BumbleBee"/> - <g:message code="default.list.label" args="[featureLabel]"/></title>
</head>

<body>
<a href="#list-feature" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[featureLabel]"/></g:link></li>
    </ul>
</div>

<div id="featureList">
    <table id="example">
        <thead>
            <tr>
                <th>Id</th>
                <th>${featureLabel}</th>
                <th>Description</th>
                <th>Developer</th>
                <th><g:message code="featurephase.tester.label" default="Tester"/> </th>
                <th>Work Effort (h)</th>
                <th>Status</th>
                <th>Completed</th>
                <th>Bugs</th>
                <th>Bug Status</th>
                <th>Bug Severity</th>
                <th>3rd Party Cases</th>
            </tr>
        </thead>
        <tbody>
            <g:set var="featureCount" value="1" />
            <g:each in="${featureInstanceList}" var="feature">
                <g:if test="${featureCount%1 == 1}">
                    <tr class="odd">
                </g:if>
                <g:else>
                    <tr class="even">
                </g:else>
                    <td>${feature.id}</td>
                    <td><g:link action="edit" id="${feature.id}">${feature.name}</g:link></td>
                    <td>${feature.description}</td>
                    <td class="center"><g:include action="userList" model="${[feature: feature, userType: "developer"]}"/></td>
                    <td class="center"><g:include action="userList" model="${[feature: feature, userType: "tester"]}"/></td>
                    <td class="center"></td>
                    <td class="center"></td>
                    <td class="center"></td>
                    <td class="center"></td>
                    <td class="center"></td>
                    <td class="center"></td>
                    <td class="center"></td>
                </tr>
            </g:each>
        </tbody>
        <tfoot>
            <tr>
                <th colspan="12"></th>
            </tr>
        </tfoot>
    </table>
</div>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        $('#example').dataTable({
            "aaSorting": [[0, "asc"]],
            "sPaginationType": "full_numbers",
            "iDisplayLength" : 50
            } );
    } );
</script>
</body>
</html>
