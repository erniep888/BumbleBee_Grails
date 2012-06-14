<div id="mainNav" class="nav">
    <ul>
        <g:if test="${!mainMenuSelection || mainMenuSelection == 'dashboard'}">
            <li><g:link class="home selected" controller="Dashboard">Dashboard</g:link></li>
        </g:if>
        <g:else>
            <li><g:link class="home" controller="Dashboard">Dashboard</g:link></li>
        </g:else>

        <g:if test="${mainMenuSelection == 'feature'}">
            <li><g:link class="feature selected" controller="Feature"><g:message code="feature.label" default="Feature"/>s</g:link></li>
        </g:if>
        <g:else>
            <li><g:link class="feature" controller="Feature"><g:message code="feature.label" default="Feature"/>s</g:link></li>
        </g:else>

        <g:if test="${mainMenuSelection == 'library'}">
            <li><g:link class="library selected" controller="Library">Library</g:link></li>
        </g:if>
        <g:else>
            <li><g:link class="library" controller="Library">Library</g:link></li>
        </g:else>

        <g:if test="${mainMenuSelection == 'administration'}">
            <li><g:link class="administration selected" controller="Administration">Administration</g:link></li>
        </g:if>
        <g:else>
            <li><g:link class="administration" controller="Administration">Administration</g:link></li>
        </g:else>
    </ul>
</div>
