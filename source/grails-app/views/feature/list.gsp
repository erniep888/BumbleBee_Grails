<%@ page import="bumblebee.Feature" %>
<head>
    <meta name="layout" content="main"/>
    <g:render template="/shared/pageTitle" model="${ [pageTitle: message(code: 'feature.label', default: 'Feature') + 's']  }"/>

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
        <li>
            <g:link class="list" action="details">
                <g:if test="${session['detailsOn']}">
                    Hide Details
                </g:if>
                <g:else>
                    Show Details
                </g:else>
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
                <th class="center">${message(code: 'feature.module.label', default: 'Module')}</th>
                <th class="left">Description</th>
                <th class="left">Developer</th>
                <th class="left"><g:message code="featurePhase.tester.label" default="Tester"/> </th>
                <th class="center">Work Effort (h)</th>
                <g:if test="${session['detailsOn']}">
                <th class="center">Status</th>
                <th class="center">Completed</th>
                <th class="center">&nbsp;&nbsp;Bugs&nbsp;&nbsp;</th>
                <th class="center">Bug Status</th>
                <th class="center">Bug Severity</th>
                <th class="center">3rd Party Cases</th>
                </g:if>
            </tr>
        </thead>
        <tbody>
        </tbody>
        <tfoot>
            <tr>
                <g:if test="${session['detailsOn']}">
                <th colspan="13"></th>
                </g:if>
                <g:else>
                <th colspan="7"></th>
                </g:else>
            </tr>
        </tfoot>
    </table>
</div>

<div id="allFeaturesLink" style="display: none"><g:createLink controller="feature" action="allFeatures"/></div>

<g:javascript src="jquery.dataTables.js"/>
<g:if test="${session['detailsOn']}">
<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        var allFeaturesLink = $('#allFeaturesLink').text(); // + '?'+ Math.round(new Date().getTime());
        $('#example').dataTable({
            "aaSorting": [[0, "asc"]],
            "sPaginationType": "full_numbers",
            "iDisplayLength" : 25 ,
            "sAjaxSource" :  allFeaturesLink,
            "aoColumns": [
                {"mDataProp": "id", "sType": "numeric", "sClass": "center"},
                {"mDataProp": "feature"},
                {"mDataProp": "module"},
                {"mDataProp": "description"},
                {"mDataProp": "developer"},
                {"mDataProp": "sme"},
                {"mDataProp": "workEffort", "sType": "numeric", "sClass": "center"},
                {"mDataProp": "status"},
                {"mDataProp": "completed"},
                {"mDataProp": "bugs"},
                {"mDataProp": "bugStatus"},
                {"mDataProp": "bugSeverity"},
                {"mDataProp": "thirdPartyCases"}
            ],
            "bStateSave": true,
            //"bRetrieve" : true,
            "bProcessing" : true
            //,"bServerSide" : true
        } );
    } );
</script>
</g:if>
<g:else>
    <script type="text/javascript" charset="utf-8">
        $(document).ready(function() {
            var allFeaturesLink = $('#allFeaturesLink').text(); // + '?'+ Math.round(new Date().getTime());
            $('#example').dataTable({
                "aaSorting": [[0, "asc"]],
                "sPaginationType": "full_numbers",
                "iDisplayLength" : 25 ,
                "sAjaxSource" :  allFeaturesLink,
                "aoColumns": [
                    {"mDataProp": "id", "sType": "numeric", "sClass": "center"},
                    {"mDataProp": "feature"},
                    {"mDataProp": "module"},
                    {"mDataProp": "description"},
                    {"mDataProp": "developer"},
                    {"mDataProp": "sme"},
                    {"mDataProp": "workEffort", "sType": "numeric", "sClass": "center"}
                ],
                "bStateSave": true,
                //"bRetrieve" : true,
                "bProcessing" : true
                //,"bServerSide" : true
            } );
        } );
    </script>
</g:else>

</body>
