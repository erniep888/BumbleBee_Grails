<%@ page import="bumblebee.FeaturePhase" %>

<div style="padding-top:10px;padding-left:10px;">
    <table border="0">
        <tr>
            <td class="vertAlign-Mid width150"><label for="upload">Upload Test Case:</label></td>
            <td>
                <g:form controller="tests" method="post" action="upload" enctype="multipart/form-data">
                    <g:hiddenField name="id" value="${testScenario?.id}"/>
                    <input type="file" name="file" size="60"/>
                    <input type="submit" value="Upload"/>
                </g:form>
            </td>
        </tr>
        <tr>
            <td class="vertAlign-Top width150">Test Cases:</td>
            <td>
                <div style="border:gray solid 1px;width:520px;height:300px;overflow-y:scroll;">
                    <table>
                        <g:each var="testCase" in="${testScenario?.testCases}">
                            <tr>
                                <td class="width200"><g:link action="viewTestCase" id="${testCase.id}">${testCase.fileName}</g:link></td>
                                <td><g:formatDate format="MM/dd/yyyy h:mm:ss a" date="${testCase?.lastUpdated}"/></td>
                                <td><g:link action="deleteTestCase" params="[id:testCase.id,testScenarioId:testScenario.id]">Delete</g:link></td>
                                <td class="width100">${Math.round(testCase.size / 1000)} kb</td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <g:form controller="tests">
                    <g:actionSubmit value="Done" action="index"/>
                </g:form>
            </td>
        </tr>
    </table>
</div>