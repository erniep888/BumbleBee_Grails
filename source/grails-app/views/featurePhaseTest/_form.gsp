<%@ page import="bumblebee.FeaturePhase" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<div class="uploadContainer">
    <table border="0">
        <tr>
            <td class="vertAlignTop">Tests</td>
            <td>
                <div class="fileList">
                    <table>
                        <g:each var="test" in="${featurePhaseInstance?.tests}">
                            <tr>
                                <td class="fileListPadding nameColumn"><g:link action="viewTest" id="${test.id}">${test.fileName}</g:link></td>
                                <td class="dateColumn"><g:formatDate format="MM/dd/yyyy h:mm:ss a" date="${test?.lastUpdated}"/></td>
                                <td class="center actionColumn"><g:link action="deleteTest" params="[featureId: params.featureId, id:params.id, artifactId:test.id]">Delete</g:link></td>
                                <td class="fileListPadding sizeColumn">${Math.round(test.size / 1000)} KB</td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td class="labelColumn uploadRow">Upload Test</td>
            <td class="uploadRow">
                <g:uploadForm name="testUpload" action="upload" controller="featurePhaseTest">
                    <g:hiddenField name="id" value="${params.id}" />
                    <g:hiddenField name="featureId" value="${params.featureId}" />
                    <input class="uploadFileName" name="contents" type="file"/>
                    <input value="Upload" type="submit"/>
                </g:uploadForm>
            </td>
        </tr>
    </table>
</div>
