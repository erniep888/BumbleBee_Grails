<%@ page import="bumblebee.FeaturePhase" %>

<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>
<div class="listContainer">
    <table border="0">
        <tr>
            <td class="vertAlignTop">Attachments</td>
            <td>
                <div class="fileList">
                    <table>
                        <g:each var="attachment" in="${featurePhaseInstance?.attachments}">
                            <tr>
                                <td class="fileListPadding fileNameColumn"><g:link action="viewAttachment" id="${attachment.id}">${attachment.fileName}</g:link></td>
                                <td class="dateColumn"><g:formatDate format="MM/dd/yyyy h:mm:ss a" date="${attachment?.lastUpdated}"/></td>
                                <td class="center actionColumn"><g:link action="deleteAttachment" params="[featureId: params.featureId, id:params.id, artifactId:attachment.id]">Delete</g:link></td>
                                <td class="fileListPadding sizeColumn">${Math.round(attachment.size / 1000)} KB</td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td class="labelColumn uploadRow">Upload Attachment</td>
            <td class="uploadRow">
                <g:uploadForm name="attachmentUpload" action="upload" controller="featurePhaseAttachment">
                    <g:hiddenField name="id" value="${params.id}" />
                    <g:hiddenField name="featureId" value="${params.featureId}" />
                    <input class="uploadFileName" name="contents" type="file"/>
                    <input value="Upload" type="submit"/>
                </g:uploadForm>
            </td>
        </tr>
    </table>
</div>
