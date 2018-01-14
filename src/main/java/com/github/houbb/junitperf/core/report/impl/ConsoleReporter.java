package com.github.houbb.junitperf.core.report.impl;

import com.github.houbb.junitperf.constant.StatusConstant;
import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.util.Map;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 命令行报告
 * 描述：将统计结果输出到命令行。
 *
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public class ConsoleReporter implements Reporter {

    private static final Log log = LogFactory.getLog(ConsoleReporter.class);

    @Override
    public String reportPath() {
        return null;
    }

    @Override
    public void report(Set<EvaluationContext> evaluationContextSet) {
        for (EvaluationContext context : evaluationContextSet) {

            StatisticsCalculator statistics = context.getStatisticsCalculator();

            String throughputStatus = getStatus(context.isTimesPerSecondAchieved());

            log.info("Started at:   {}", context.getStartTime());
            log.info("Invocations:  {}", statistics.getEvaluationCount());
            log.info("Success:  {}", statistics.getEvaluationCount() - statistics.getErrorCount());
            log.info("Errors:   {}", statistics.getErrorCount());
            log.info("Thread Count: {}", context.getConfigThreads());
            log.info("Warm up:      {}ms", context.getConfigWarmUp());
            log.info("Execution time: {}ms", context.getConfigDuration());
            log.info("Throughput:     {}/s (Required: {}/s) - {}",
                    context.getThroughputQps(),
                    context.getRequireTimesPerSecond(),
                    throughputStatus);
            log.info("Min latency:   {}ms (Required: {}ms) - {}",
                    statistics.getMinLatency(MILLISECONDS),
                    context.getRequireMin(),
                    getStatus(context.isMinAchieved()));
            log.info("Max latency:    {}ms (Required: {}ms) - {}",
                    statistics.getMaxLatency(MILLISECONDS),
                    context.getRequireMax(),
                    getStatus(context.isMaxAchieved()));
            log.info("Ave latency:    {}ms (Required: {}ms) - {}",
                    statistics.getMeanLatency(MILLISECONDS),
                    context.getRequireAverage(),
                    getStatus(context.isAverageAchieved()));


            for (Map.Entry<Integer, Float> entry : context.getRequirePercentilesMap().entrySet()) {
                Integer percentile = entry.getKey();
                Float threshold = entry.getValue();
                boolean result = context.getRequirePercentilesResults().get(percentile);
                String percentileStatus = getStatus(result);
                log.info("Percentile: {}%%    {}ms (Required: {}ms) - {}",
                        percentile,
                        statistics.getLatencyPercentile(percentile, MILLISECONDS),
                        threshold,
                        percentileStatus);

            }
        }
    }


    private String getStatus(boolean status) {
        if (status) {
            return StatusConstant.PASSED;
        }
        return StatusConstant.FAILED;
    }

}
