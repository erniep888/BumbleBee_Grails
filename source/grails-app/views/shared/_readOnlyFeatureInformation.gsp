<%@ page import="bumblebee.Feature" %>

<h1><g:message code="feature.label" /> ${featureInstance.name}</h1>
<div id="editLink"><g:link controller="Feature" action="edit" params="[id: featureInstance.id]">edit</g:link></div>
<div id="deleteLink"><g:link controller="Feature" action="delete" params="[id: featureInstance.id]">delete</g:link></div>
<table class="featureInformation">
    <tr>
        <td class="labelColumn"><g:message code="feature.description.label" default="Description"/></td>
        <td class="dataColumn">${featureInstance.description}</td>
    </tr>
    <tr>
        <td class="labelColumn"><g:message code="feature.category.label" default="Category"/></td>
        <td class="dataColumn">${featureInstance.category}</td>
    </tr>
</table>

