<%@ page import="bumblebee.Feature" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'feature.label', default: 'Feature')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-feature" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="featureList">
    <table id="example">
        <thead>
            <tr>
                <th>Id</th>
                <th><g:message code="feature.label" default="Feature"/></th>
                <th>Description</th>
                <th>Developer</th>
                <th>SME</th>
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
            <tr class="odd">
                <td>1</td>
                <td><a href="http://google.com">R57200</a></td>
                <td>Inventory Master</td>
                <td class="center">John Smith</td>
                <td class="center">Sally Doe</td>
                <td class="center">2</td>
                <td class="center">In Progress</td>
                <td class="center"></td>
                <td class="center"></td>
                <td class="center"></td>
                <td class="center"></td>
                <td class="center"><a href="http://google.com">1</a></td>
            </tr>
            <tr class="even">
                <td>2</td>
                <td><a href="http://google.com">R57201</a></td>
                <td>Inventory Summary</td>
                <td class="center">John Smith</td>
                <td class="center">Sam Bosnac</td>
                <td class="center">5</td>
                <td class="center">Complete</td>
                <td class="center">06/12/2012</td>
                <td class="center"></td>
                <td class="center"></td>
                <td class="center"></td>
                <td class="center"><a href="http://google.com">1</a></td>
            </tr>
            <tr class="odd">
                <td>3</td>
                <td><a href="http://google.com">R57202</a></td>
                <td>Asset Ledger</td>
                <td class="center">Ramon Devan</td>
                <td class="center">Sam Bosnac</td>
                <td class="center">3</td>
                <td class="center">In Progress</td>
                <td class="center"></td>
                <td class="center"><a href="http://google.com">3</a></td>
                <td class="center">assigned</td>
                <td class="center">major</td>
                <td class="center"><a href="http://google.com">1</a></td>
            </tr>
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
