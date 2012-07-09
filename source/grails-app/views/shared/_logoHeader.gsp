<div id="logo" role="banner">
    <table >
        <tr>
            <td><a href="http://myportal"><img src="${resource(dir: 'images', file: 'logo.png')}" alt="SIMS"/></a></td>
            <td>
                <div id="title">
                    <g:message code="app.userfriendly.applicationname" default=""/>
                    <div id="login">Welcome, <g:include controller="administrationContributor" action="getUsernameOrFullName"/></div>
                </div>
            </td>
        </tr>
    </table>
</div>
<div class="projectTitle">${currentProject.name}</div>