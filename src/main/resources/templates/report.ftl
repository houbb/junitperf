<!doctype html>
<html>
<head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <meta charset="utf-8">
    <title>JUnit Performance Report</title>

</head>
<body style='font-family:Verdana;'>
<center>
    <h1 style='color:#044e9b'>JUnit Performance Report</h1>
    <hr/>
    <br/>
    <table border='1' cellspacing='0' cellpadding='3px' style='border-color:#eee'>
        <tr>
            <th style='background-color:#ffffdd; color:#044e9b'>&nbsp;&nbsp;&nbsp;</th>
            <th style='background-color:#ffffdd; color:#044e9b'>Tests</th>
    <tr>

    <#list contextData as context>
        <tr>
            <!-- Success: #2b67a4 failure: #d9534f -->
            <#if context.evaluationResult.isSuccessful()>
                <td style="background: #2b67a4;">&nbsp;</td>
            <#else>
                <td style="background: #d9534f;">&nbsp;</td>
            </#if>
            <td><a href='#${context.methodName}'>${context.methodName}</a></td>
        </tr>
    </#list>

    </table>
    <br/>
    <hr/>


<#list contextData as context>
    <a name='${context.methodName}'><h2 style='color:#2b67a4'>${context.methodName}</h2></a>
    <table style="width: 960px;">
        <tr>
        <#--一半图片-->
            <td>
                <!-- ADD scatter Chart here!! -->
                <script type="text/javascript">
                    google.charts.load('current', {'packages': ['corechart']});
                    google.charts.setOnLoadCallback(drawChart);

                    function drawChart() {
                        var data = google.visualization.arrayToDataTable([
                            ['Percentile', 'Latency', {role: "tooltip"}],
                            <#list 1..100 as i>
                                [ ${i}, ${context.statisticsCalculator.getLatencyPercentile(i, milliseconds)} , "${i}% of executions ≤ ${context.statisticsCalculator.getLatencyPercentile(i, milliseconds)}ms"],
                            </#list>
                        ]);
                        var options = {
                            title: 'Latency percentile Distribution',
                            hAxis: {title: 'Percentile', minValue: 0, maxValue: 100},
                            vAxis: {title: 'Latency', minValue: 0, maxValue: 15},
                            legend: 'none',
                            lineWidth: 1,
                            pointSize: 7,
                            dataOpacity: 0.5
                        };
                        var chart = new google.visualization.ScatterChart(document.getElementById('${context.methodName}'));
                        chart.draw(data, options);
                    }
                </script>

                <div id="${context.methodName}" style="width: 600px; height: 400px;"></div>
            </td>


        <#--一半统计信息-->
            <td>

                <table style='font-family:sans-serif;'>
                    <tr>
                        <th align='right'>Started at:</th>
                        <td align='right' colspan='2'><b>${context.startTime}</b></td>
                    </tr>

                    <tr>
                        <th align='right' valign='top'>Invocations:</th>
                        <td align='right'>${context.statisticsCalculator.evaluationCount}</td>
                        <td align='right'></td>
                    </tr>
                    <tr>
                        <th align='right' valign='top'>- Success:</th>
                        <td align='right'>${context.statisticsCalculator.evaluationCount - context.statisticsCalculator.errorCount}</td>
                        <td align='right'></td>
                    </tr>


                    <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
                    <tr>
                        <th align='right' valign='top'>Thread Count:</th>
                        <td align='right'>${context.evaluationConfig.configThreads}</td>
                        <td align='right'></td>
                    </tr>
                    <tr>
                        <th align='right' valign='top'>Warm up:</th>
                        <td align='right'>${context.evaluationConfig.configWarmUp} ms</td>
                        <td align='right'></td>
                    </tr>
                    <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
                    <tr valign='top'>
                        <th>&nbsp;</th>
                        <th>Measured<br/>(system)</th>
                        <th>Required</th>
                    </tr>
                    <tr>
                        <th align='right' valign='top'>Execution time:</th>
                        <td align='right'>${context.evaluationConfig.configDuration} ms</td>
                        <td align='right'></td>
                    </tr>

                    <tr>
                        <#assign colour = context.evaluationResult.isTimesPerSecondAchieved() ? string("#2b67a4", "#d9534f")>

                        <th align='right' valign='top'><b style='color:${colour}'>Throughput:</b></th>
                        <td align='right'><b style='color:${colour};'>${context.evaluationResult.getThroughputQps()} / s</b></td>
                        <td align='right'><b style='color:${colour};'>${context.evaluationRequire.requireTimesPerSecond} / s</b></td>
                    </tr>

                    <tr>
                        <#assign colour = context.evaluationResult.isMinAchieved() ? string("#2b67a4", "#d9534f")>
                        <th align='right' valign='top'><b style='color:${colour}'>Min. latency:</b></th>
                        <td align='right'><b style='color:${colour}'>${context.statisticsCalculator.getMinLatency(milliseconds)} ms</b></td>
                        <td align='right'><b style='color:${colour}'>${context.evaluationRequire.requireMin} ms</b></td>
                    </tr>

                    <tr>
                        <#assign colour = context.evaluationResult.isAverageAchieved() ? string("#2b67a4", "#d9534f")>
                        <th align='right' valign='top'><b style='color:${colour}'>Average latency:</b></th>
                        <td align='right'><b style='color:${colour}'>${context.statisticsCalculator.getMeanLatency(milliseconds)} ms</b></td>
                        <td align='right'><b style='color:${colour}'>${context.evaluationRequire.requireAverage} ms</b></td>
                    </tr>

                    <tr>
                        <#assign colour = context.evaluationResult.isMaxAchieved() ? string("#2b67a4", "#d9534f")>
                        <th align='right' valign='top'><b style='color:${colour}'>Max latency:</b></th>
                        <td align='right'><b style='color:${colour}'>${context.statisticsCalculator.getMaxLatency(milliseconds)} ms</b></td>
                        <td align='right'><b style='color:${colour}'>${context.evaluationRequire.requireMax} ms</b></td>
                    </tr>

                    <#assign percentMap = context.evaluationRequire.getRequirePercentilesMap()>

                    <#list percentMap.keySet() as entryKey>
                        <#assign colour = context.evaluationResult.getIsPercentilesAchievedMap().get(entryKey) ? string("#2b67a4", "#d9534f")>

                        <tr>
                            <th align='right' valign='top'><b style='color:${colour}'>Percent ${entryKey}:</b></th>
                            <td align='right'><b style='color:${colour}'>${context.statisticsCalculator.getLatencyPercentile(entryKey, milliseconds)} ms</b></td>
                            <td align='right'><b style='color:${colour}'>${percentMap.get(entryKey)} ms</b></td>
                        </tr>
                    </#list>

                </table>

            </td>

        </tr>


    </table>
</#list>

    <hr/>
    <div style='color:#044e9b;'>Report created by <a href='https://github.com/houbb/junitperf'>JunitPerf</a></div>
</center>
</body>
</html>
