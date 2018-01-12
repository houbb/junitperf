package com.github.houbb.junitperf.core.report.impl;

import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.util.Set;

/**
 * 命令行报告
 * 描述：将统计结果输出到命令行。
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
        for(EvaluationContext context : evaluationContextSet) {

            StatisticsCalculator statistics = context.getStatisticsCalculator();
//            String throughputStatus = context.isThroughputAchieved() ? PASSED : FAILED;
//            String errorRateStatus = context.isErrorThresholdAchieved() ? PASSED : FAILED;

//            log.info("Started at:   {}", context.get);
            log.info("Invocations:  {}", statistics.getEvaluationCount());
            log.info("  - Success:  {}", statistics.getEvaluationCount());
            log.info("  - Errors:   {}", statistics.getErrorCount());
            log.info("");
            log.info("Thread Count: {}", context.getConfigThreads());
            log.info("Warm up:      {}ms", context.getConfigWarmUp());
            log.info("");
            log.info("Execution time: {}ms", context.getConfigDuration());
        }
    }

}
