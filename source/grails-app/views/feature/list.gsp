<%@ page import="bumblebee.Feature" %>
<head>
    <meta name="layout" content="main"/>
    <g:render template="/shared/pageTitle" model="${ [pageTitle: message(code: 'feature.label', default: 'Feature')] }"/>
</head>

<body>
<a href="#list-feature" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li>
            <g:link class="create" action="create">
                <g:message code="default.new.label" args="[message(code: 'feature.label', default: 'Feature')]"/>
            </g:link>
        </li>
    </ul>
</div>
<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<div id="featureList">
    <table id="example">
        <thead>
            <tr>
                <th class="center">Id</th>
                <th class="left">${message(code: 'feature.label', default: 'Feature')}</th>
                <th class="left">Description</th>
                <th class="left">Developer</th>
                <th class="left"><g:message code="featurePhase.tester.label" default="Tester"/> </th>
                <th class="center">Work Effort (h)</th>
                <th class="center">Status</th>
                <th class="center">Completed</th>
                <th class="center">Bugs</th>
                <th class="center">Bug Status</th>
                <th class="center">Bug Severity</th>
                <th class="center">3rd Party Cases</th>
                <th class="center">Off Shore</th>
            </tr>
        </thead>
        <tbody>
            <g:set var="featureCount" value="${1}" />
            <g:each in="${featureInstanceList}" var="featureInstance">
                <g:if test="${(featureCount%1) == 1}">
                    <tr class="odd">
                </g:if>
                <g:else>
                    <tr class="even">
                </g:else>
                    <td class="center">${featureInstance.id}</td>
                    <td class="left"><g:link controller="featurePhaseGeneral" action="edit"
                                params="[featureId: featureInstance.id]" id="${1}">${featureInstance.name}</g:link></td>
                    <td class="left">${featureInstance.description}</td>
                    <td class="left"><g:include action="userList" model="${[feature: featureInstance, userType: "developer"]}"/></td>
                    <td class="left"><g:include action="userList" model="${[feature: featureInstance, userType: "tester"]}"/></td>
                    <td class="center"><g:include action="workEffort" model="${[feature: featureInstance]}"/></td>
                    <td class="center"><g:include action="featureStatus" model="${[feature: featureInstance]}"/></td>
                    <td class="center"><g:include action="featureCompletion" model="${[feature: featureInstance]}"/></td>
                    <td class="center"></td>
                    <td class="center"></td>
                    <td class="center"></td>
                    <td class="center"></td>
                    <td class="center"><g:include action="offShore" model="${[feature: featureInstance]}"/></td>
                </tr>
            </g:each>
        </tbody>
        <tfoot>
            <tr>
                <th colspan="13"></th>
            </tr>
        </tfoot>
    </table>
</div>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        $('#example').dataTable({
            "aaSorting": [[0, "asc"]],
            "sPaginationType": "full_numbers",
            "iDisplayLength" : 25
            } );
    } );
</script>
</body>
