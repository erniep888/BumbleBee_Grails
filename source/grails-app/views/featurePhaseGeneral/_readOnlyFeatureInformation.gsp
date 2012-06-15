<%@ page import="bumblebee.Feature" %>

<h1><g:message code="feature.label" /> ${featureInstance.name}</h1>
<div id="deleteLink">delete</div>
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

