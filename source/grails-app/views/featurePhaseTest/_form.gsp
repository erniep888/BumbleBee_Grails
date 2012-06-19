<%@ page import="bumblebee.FeaturePhase" %>

<div class="uploadContainer">
    <table border="0">
        <tr>
            <td class="width100">Upload Test</td>
            <td>
                <g:uploadForm name="testUpload" action="upload" controller="featurePhaseTest">
                    <g:hiddenField name="id" value="${params.id}" />
                    <g:hiddenField name="featureId" value="${params.featureId}" />
                    <input name="contents" type="file" size="60"/>
                    <input value="Upload" type="submit"/>
                </g:uploadForm>
            </td>
        </tr>
        <tr>
            <td class="vertAlignTop width100">Tests</td>
            <td>
                <div class="fileList">
                    <table>
                        <g:each var="test" in="${featurePhaseInstance?.tests}">
                            <tr>
                                <td class="width200"><g:link action="viewTest" id="${test.id}">${test.fileName}</g:link></td>
                                <td><g:formatDate format="MM/dd/yyyy h:mm:ss a" date="${test?.lastUpdated}"/></td>
                                <td><g:link action="deleteTest" params="[id:test.id,featurePhaseId:featurePhase.id]">Delete</g:link></td>
                                <td class="width100">${Math.round(test.size / 1000)} kb</td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <g:form controller="featurePhaseTest">
                    <g:actionSubmit value="Done" action="edit"/>
                </g:form>
            </td>
        </tr>
    </table>
</div>
