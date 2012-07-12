<%--
  Created by IntelliJ IDEA.
  bumblebee.Contributor: pascherk
  Date: 6/6/12
  Time: 5:09 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <g:render template="/shared/pageTitle" model="${ [pageTitle: "Dashboard"] }"/>
</head>

<body>
<div class="nav" role="navigation">
    %{--<ul>--}%
        %{--<li>--}%
            %{--<g:link action="byPhase">--}%
                %{--<g:message code="dashboard.menu.byPhase.label"/>--}%
            %{--</g:link>--}%
        %{--</li>--}%
        %{--<li>--}%
            %{--<g:link action="byProject">--}%
                %{--<g:message code="dashboard.menu.byCategory.label" args="[message(code: 'feature.category.label', default: 'Project')]"/>--}%
            %{--</g:link>--}%
        %{--</li>--}%
    %{--</ul>--}%
</div>
<g:if test="${flash.message}">
    <div class="errors" role="status">${flash.message}</div>
</g:if>

<table>
    <tr>
        <td style="padding:2em;">
            <div style="text-align: center;"><g:message code="feature.label"/> Status</div>
            <div id="chart1" style="height: 250px; width: 450px;">
            </div>
        </td>
        <td>
            <div style="text-align: center;">Status Complete/Incomplete By <g:message code="feature.category.label"/></div>
            <div id="chart2" style="height: 250px; width: 450px;">
            </div>
        </td>
    </tr>
</table>


<!--[if lte IE 9]><g:javascript src="flotr2.ie.min.js"/><![endif]-->
<g:javascript src="flotr2.min.js"/>
<div id="allFeatureStatusCountLink" style="display: none"><g:createLink controller="dashboard" action="allFeatureStatusCount"/></div>
<div id="statusCompleteByCategory" style="display: none"><g:createLink controller="dashboard" action="statusCompleteByCategory"/></div>

<script type="text/javascript">
    $(document).ready(function(){
        var allFeatureStatusCountLink = $('#allFeatureStatusCountLink').text()

        $.getJSON(allFeatureStatusCountLink + '?'+ Math.round(new Date().getTime()), function(allCounts) {
            Flotr.draw(document.getElementById('chart1'), allCounts.counts,
            {
                HtmlText : false,
                grid : {
                    verticalLines : false,
                    horizontalLines : false,
                    backgroundColor : {
                        colors : [[0,'#fff'], [1,'#ccc']],
                        start : 'top',
                        end : 'bottom'
                    }

                },
                xaxis : { showLabels : false },
                yaxis : { showLabels : false },
                pie : {
                    show : true,
                    explode : 5
                },
                mouse : {
                    track : true,
                    relative: true,
                    trackDecimals: 0,
                    trackFormatter: function(obj){ return 'Count = ' + obj.y; }
                },
                legend : {
                    position : 'sw',
                    backgroundColor : '#ddd'
                }
            });
        });


        var statusCompleteByCategory = $('#statusCompleteByCategory').text()
        $.getJSON(statusCompleteByCategory + '?'+ Math.round(new Date().getTime()), function(countsByCategory) {

            var
                    d1 = [],
                    d2 = [],
                    graph, i;

            for (i = 0; i < 10; i+=3) {
                d1.push([i, Math.random()*10]);
                d2.push([i, Math.random()*10]);
            }
            graph = Flotr.draw(document.getElementById('chart2'),
                    countsByCategory.counts,
//                    [
//                        { data : d1, label : 'Complete' },
//                        { data : d2, label : 'Incomplete' }
//                    ],
            {
                HtmlText : false,
                grid : {
                    verticalLines : false,
                    horizontalLines : false,
                    backgroundColor : {
                        colors : [[0,'#fff'], [1,'#ccc']],
                        start : 'top',
                        end : 'bottom'
                    }

                },
                xaxis : {
                    showLabels : true,
                    labelsAngle: -45,
                    ticks: [[0, 'Finance - AR-Auto Cash'], [2, 'Distribution - Seltec Billing'], [4, 'Maintenance - Interfaces/Core']]
                },
                yaxis : { showLabels : true },
                bars : {
                    show: true,
                    stacked: true,
                    horizontal: false
                },
                mouse : {
                    track : true,
                    relative: true,
                    trackDecimals: 0,
                    trackFormatter: function(obj){ return 'Count = ' + obj.y; }
                },
                legend : {
                    position : 'nw',
                    backgroundColor : '#ddd'
                }
            });
        });
    });



</script>
</body>

</html>