<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="${message(code: "app.userfriendly.applicationAcronym")}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.dataTables.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    <g:layoutHead/>
    <r:require module="jquery"/>
    <r:layoutResources />
</head>
<body>
<div id="logo" role="banner">
    <table >
        <tr>
            <td><a href="http://myportal"><img src="${resource(dir: 'images', file: 'logo.png')}" alt="SIMS"/></a></td>
            <td>
                <div id="title">
                    <g:message code="app.userfriendly.applicationname" default=""/>
                    <div id="login">Welcome, <%= request.remoteUser %></div>
                </div>
            </td>
        </tr>
    </table>
</div>
<g:render template="/shared/mainMenu"/>
<a href="#create-feature" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="list" controller="feature" action="list"><g:message code="default.list.label" args="[message(code: 'feature.label', default: 'Feature')]"/></g:link></li>
    </ul>
</div>

<div class="content" role="main">
    <g:render template="/shared/readOnlyFeatureInformation"/>

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
<div class="footer">
    <table>
        <tr>
            <td>
                <div id="footerMessage">&nbsp;Ready</div>
            </td>
            <td>
                <div id="footerAppInfo"><g:message code="app.userfriendly.applicationname" default=""/>  (<g:meta name="app.version"/>)</div>
            </td>
        </tr>
    </table>
</div>
<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
<g:javascript library="application"/>
<r:layoutResources />
</body>
</html>