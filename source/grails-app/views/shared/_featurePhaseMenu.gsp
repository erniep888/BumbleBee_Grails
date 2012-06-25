<div id="phaseNav" class="nav">
    <ul>
        <g:if test="${!featurePhaseMenuSelection || featurePhaseMenuSelection == 'general'}">
            <li><g:link class="selected" controller="FeaturePhaseGeneral" action="Edit" params="${params}">General</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="FeaturePhaseGeneral" action="Edit" params="${params}">General</g:link></li>
        </g:else>

        <g:if test="${!featurePhaseMenuSelection || featurePhaseMenuSelection == 'tests'}">
            <li><g:link class="selected" controller="FeaturePhaseTest" action="Edit" params="${params}">Tests</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="FeaturePhaseTest" action="Edit" params="${params}">Tests</g:link></li>
        </g:else>

        <g:if test="${!featurePhaseMenuSelection || featurePhaseMenuSelection == 'attachments'}">
            <li><g:link class="selected" controller="FeaturePhaseAttachment" action="Edit" params="${params}">Attachments</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="FeaturePhaseAttachment" action="Edit" params="${params}">Attachments</g:link></li>
        </g:else>

        <g:if test="${!featurePhaseMenuSelection || featurePhaseMenuSelection == 'bugs'}">
            <li><g:link class="selected" controller="FeaturePhaseBug" action="Edit" params="${params}">Bugs</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="FeaturePhaseBug" action="Edit" params="${params}">Bugs</g:link></li>
        </g:else>

        <g:if test="${!featurePhaseMenuSelection || featurePhaseMenuSelection == 'links'}">
            <li><g:link class="selected" controller="FeaturePhaseLink" action="Edit" params="${params}">Links</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="FeaturePhaseLink" action="Edit" params="${params}">Links</g:link></li>
        </g:else>

        <g:if test="${!featurePhaseMenuSelection || featurePhaseMenuSelection == 'cases'}">
            <li><g:link class="selected" controller="FeaturePhaseCase" action="Edit" params="${params}">Third Party Cases</g:link></li>
        </g:if>
        <g:else>
            <li><g:link controller="FeaturePhaseCase" action="Edit" params="${params}">Third Party Cases</g:link></li>
        </g:else>
    </ul>
</div>
