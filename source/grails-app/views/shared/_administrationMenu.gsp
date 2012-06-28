<div id="administrationNav" class="nav">
    <ul>
        <g:if test="${!administrationMenuSelection || administrationMenuSelection == 'contributors'}">
            <li><g:link class="selected" controller="AdministrationContributor" action="list" params="${params}">Contributors</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="AdministrationContributor" action="list" params="${params}">Contributors</g:link></li>
        </g:else>

        <g:if test="${!administrationMenuSelection || administrationMenuSelection == 'administrators'}">
            <li><g:link class="selected" controller="AdministrationAdministrator" action="list" params="${params}">Administrators</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="AdministrationAdministrator" action="list" params="${params}">Administrators</g:link></li>
        </g:else>

        <g:if test="${!administrationMenuSelection || administrationMenuSelection == 'activeDirectory'}">
            <li><g:link class="selected" controller="AdministrationActiveDirectory" action="index" params="${params}">Active Directory</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="AdministrationActiveDirectory" action="index" params="${params}">Active Directory</g:link></li>
        </g:else>

        <g:if test="${!administrationMenuSelection || administrationMenuSelection == 'deletedObjects'}">
            <li><g:link class="selected" controller="AdministrationDeletedObject" action="list" params="${params}">Deleted Objects</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="AdministrationDeletedObject" action="list" params="${params}">Deleted Objects</g:link></li>
        </g:else>
    </ul>
</div>
