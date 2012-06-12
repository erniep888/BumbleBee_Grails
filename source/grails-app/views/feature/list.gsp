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
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="featureList">
    <table id="example">
        <thead>
            <tr>
                <th>Rendering engine</th>
                <th>Browser</th>
                <th>Platform(s)</th>
                <th>Engine version</th>
                <th>CSS grade</th>
            </tr>
        </thead>
        <tbody>
            <tr class="odd gradeX">
                <td>Trident</td>
                <td><a href="http://google.com">Internet Explorer 4.0</a></td>
                <td>Win 95+</td>
                <td class="center"> 4</td>
                <td class="center">X</td>
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <th>Rendering engine</th>
                <th>Browser</th>
                <th>Platform(s)</th>
                <th>Engine version</th>
                <th>CSS grade</th>
            </tr>
        </tfoot>
    </table>
</div>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        $('#example').dataTable( {
            "aoColumns": [
                null,
                { "asSorting": [ "asc" ] },
                { "asSorting": [ "desc", "asc", "asc" ] },
                { "asSorting": [ "desc" ] },
                null
            ]
        } );
    } );
</script>
</body>
</html>
