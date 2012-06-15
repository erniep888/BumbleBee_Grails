<div id="phaseNav" class="nav">
    <ul>
        <g:each in="${phases}" var="phase">
            <g:if test="${!selectedPhase || selectedPhase == phase}">
                <li><g:link class="selected" controller="${params.controller}" action="Edit" params="${params}">${phase.name}</g:link></li>
            </g:if>
            <g:else>
                <li><g:link controller="${params.controller}" action="Edit" id="${phase.id}" params="${[featureId: params.featureId]}">${phase.name}</g:link></li>
            </g:else>
        </g:each>
    </ul>
</div>
