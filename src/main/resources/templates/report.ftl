<!doctype html>
<html>
<head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <meta charset="utf-8">
    <title>${className}-JUnit Performance Report</title>

    <style>

        a {
            color: #2a7ae2;
            text-decoration: none;
        }

        a:visited {
            color: #2A7AE2;
        }

        a:hover {
            color: #2a7ae2;
            text-decoration: underline;
        }

        body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: Verdana, serif;
            background-color: rgb(246, 246, 246);
        }

        header {
            height: 66px;
            width: auto;
            line-height: 1.8;
            color: #044e9b;
            text-align: center;
            font-size: 36px;
            border-bottom: rgb(238, 239, 237) 1px solid;
        }

        section {
            /*padding: 25px 0 100px 100px;*/
            /*text-align: center;*/
            width: 960px;
            margin: 0 auto;
            padding-top: 20px;
        }

        section aside {
            float: left;
            width: 220px;
            border: rgb(238, 239, 237) 1px solid;
            background: #fff;
            min-height: 580px;
            /*position: relative;*/
            /*margin-right: 40px;*/
        }

        aside .list-group ul {
            list-style: none;
            width: 200px;
            margin: 6px auto;
            background: rgb(248, 246, 243);
            padding: 0;
            overflow: auto;
        }

        aside .list-group li {
            border-bottom: rgb(238, 239, 237) 1px solid;
            height: 35px;
            line-height: 35px;
            text-align: center;
        }
        aside .list-group li:hover {
            color: #111111;
        }

        aside .list-group li.active {
            font-size: 14px;
            font-weight: bold;
        }

        aside li.borderRightSuccess {
            border-right: green 3px solid;
        }

        aside li.borderRightFail {
            border-right: red 3px solid;
        }

        aside li.borderRightWarn {
            border-right: gold 3px solid;
        }

        aside .list-group a {
            color: #111;
            font-size: 13px;
            text-decoration: none;
        }

        aside .list-group a:hover, .list-group a:visited, .list-group a:active {
            color: #aaaaaa;
            text-decoration: none;
        }

        section .main-wrapper {
            float: left;
            margin-left: 40px;
        }

        section .test-method {
            background: #fff;
            width: 660px;
            min-height: 580px;
            border-bottom: rgb(238, 239, 237) 1px solid;
            margin-bottom: 10px;
            text-align: center;
        }

        section .test-method .test-method-name {
            float: right;
            font-size: 10px;
            color: #bbb;
            margin-top: 5px;
            margin-right: 5px;
        }

        section .test-method-img {
            width: 400px;
            height: 400px;
            margin: 0 auto;
        }

        section .data-wrapper {
            font-size: 12px;
            margin-top: 20px;
            text-align: left;
        }

        section .data-wrapper .left-data {
            width: 45%;
            height: auto;
            border-right: #4490f7 1px solid;
            float: left;
        }

        section .data-wrapper .right-data {
            width: 50%;
            height: auto;
            float: right;
        }

        section .data-wrapper .table td, .table th {
            padding-right: 0.5rem;
        }

        footer {
            text-align: center;
            width: 100%;
            height: 50px;
            line-height: 50px;
            position: fixed;
            bottom: 0;
            font-size: 14px;
            color: #aaa;
            border-top: rgb(238, 239, 237) 1px solid;
        }

        footer #top {
            float: right;
            margin-right: 50px;
        }

    </style>
</head>

<body>

<header>${i18n.junit_performance_report}</header>

<section>
    <aside>
        <div class="list-group">
            <ul>
                <#list contextData as context>
                    <#assign active = (context_index==0) ? string("active", "")>
                    <#if context.evaluationResult.isSuccessful()>
                        <li class="borderRightSuccess ${active}"><a
                                href='#${context.methodName}'>${context.methodName}</a>
                        </li>
                    <#else>
                        <li class="borderRightFail ${active}"><a
                                href='#${context.methodName}'>${context.methodName}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
        </div>
    </aside>

    <div class="main-wrapper">

    <#list contextData as context>

        <div id="${context.methodName}" class="test-method">
            <span class="test-method-name">${context.methodName}</span>

            <div id="${context.methodName}-img" class="test-method-img">
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
                        var chart = new google.visualization.ScatterChart(document.getElementById('${context.methodName}-img'));
                        chart.draw(data, options);
                    }
                </script>
            </div>

            <div class="data-wrapper">
                <div class="left-data">
                    <ul>
                        <li><label>${i18n.invocations}
                            : </label><span>${context.statisticsCalculator.evaluationCount}</span>
                        </li>
                        <li>
                            <label>${i18n.success}: </label><span>${context.statisticsCalculator.evaluationCount - context.statisticsCalculator.errorCount}</span>
                        </li>
                        <li><label>${i18n.thread_count}
                            : </label><span>${context.evaluationConfig.configThreads}</span></li>

                        <li><label>${i18n.warm_up}
                            : </label><span>${context.evaluationConfig.configWarmUp} ms</span></li>
                        <li><label>${i18n.execution_time}
                            : </label><span>${context.evaluationConfig.configDuration} ms</span>
                        </li>
                        <li><label>${i18n.started_at}: </label><span>${context.startTime}</span>
                        </li>
                    </ul>
                </div>

                <div class="right-data">
                    <table class="table">
                        <thead>
                        <tr>
                            <td>${i18n.type}</td>
                            <td>${i18n.actual}</td>
                            <td>${i18n.required}</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#assign tt_c = context.evaluationResult.isTimesPerSecondAchieved() ? string("#00000", "#ec971f")>
                        <#assign min_c = context.evaluationResult.isMinAchieved() ? string("#00000", "#ec971f")>
                        <#assign avg_c = context.evaluationResult.isAverageAchieved() ? string("#00000", "#ec971f")>
                        <#assign max_c = context.evaluationResult.isMaxAchieved() ? string("#00000", "#ec971f")>

                        <tr>
                            <td>${i18n.throughput}</td>
                            <td style="color: ${tt_c}">${context.evaluationResult.getThroughputQps()} / s</td>
                            <td>${context.evaluationRequire.requireTimesPerSecond} / s</td>
                        </tr>
                        <tr>
                            <td>${i18n.min_latency}</td>
                            <td style="color: ${min_c}">${context.statisticsCalculator.getMinLatency(milliseconds)} ms</td>
                            <td>${context.evaluationRequire.requireMin} ms</td>
                        </tr>
                        <tr>
                            <td>${i18n.avg_latency}</td>
                            <td style="color: ${avg_c}">${context.statisticsCalculator.getMeanLatency(milliseconds)} ms</td>
                            <td>${context.evaluationRequire.requireAverage} ms</td>
                        </tr>
                        <tr>
                            <td>${i18n.max_latency}</td>
                            <td style="color: ${max_c}">${context.statisticsCalculator.getMaxLatency(milliseconds)} ms</td>
                            <td>${context.evaluationRequire.requireMax} ms</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>

    </#list>

    </div>


</section>

<footer>
    <div>
    ${i18n.report_created_by} <a href='https://github.com/houbb/junitperf'>JunitPerf</a>
        <a href="#" id="top">${i18n.top}</a>
    </div>
</footer>

</body>
</html>


