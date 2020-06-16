package com.github.houbb.junitperf.core.report.impl;

import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.constant.enums.StatusEnum;
import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationConfig;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationRequire;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationResult;
import com.github.houbb.junitperf.util.ConsoleUtil;
import org.apiguardian.api.API;

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
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class ConsoleReporter implements Reporter {

    @Override
    public void report(Class testClass, Set<EvaluationContext> evaluationContextSet) {
        for (EvaluationContext context : evaluationContextSet) {

            StatisticsCalculator statistics = context.getStatisticsCalculator();
            EvaluationConfig evaluationConfig = context.getEvaluationConfig();
            EvaluationRequire evaluationRequire = context.getEvaluationRequire();
            EvaluationResult evaluationResult = context.getEvaluationResult();

            String throughputStatus = getStatus(evaluationResult.isTimesPerSecondAchieved());

            ConsoleUtil.info("Started at:   {}", context.getStartTime());
            ConsoleUtil.info("Invocations:  {}", statistics.getEvaluationCount());
            ConsoleUtil.info("Success:  {}", statistics.getEvaluationCount() - statistics.getErrorCount());
            ConsoleUtil.info("Errors:   {}", statistics.getErrorCount());
            ConsoleUtil.info("Thread Count: {}", evaluationConfig.getConfigThreads());
            ConsoleUtil.info("Warm up:      {}ms", evaluationConfig.getConfigWarmUp());
            ConsoleUtil.info("Execution time: {}ms", evaluationConfig.getConfigDuration());
            ConsoleUtil.info("Throughput:     {}/s (Required: {}/s) - {}",
                    evaluationResult.getThroughputQps(),
                    evaluationRequire.getRequireTimesPerSecond(),
                    throughputStatus);
            ConsoleUtil.info("Min latency:   {}ms (Required: {}ms) - {}",
                    statistics.getMinLatency(MILLISECONDS),
                    evaluationRequire.getRequireMin(),
                    getStatus(evaluationResult.isMinAchieved()));
            ConsoleUtil.info("Max latency:    {}ms (Required: {}ms) - {}",
                    statistics.getMaxLatency(MILLISECONDS),
                    evaluationRequire.getRequireMax(),
                    getStatus(evaluationResult.isMaxAchieved()));
            ConsoleUtil.info("Avg latency:    {}ms (Required: {}ms) - {}",
                    statistics.getMeanLatency(MILLISECONDS),
                    evaluationRequire.getRequireAverage(),
                    getStatus(evaluationResult.isAverageAchieved()));

            // 输出内存信息
            reportMemory(statistics);

            for (Map.Entry<Integer, Float> entry : evaluationRequire.getRequirePercentilesMap().entrySet()) {
                Integer percentile = entry.getKey();
                Float threshold = entry.getValue();
                boolean result = evaluationResult.getIsPercentilesAchievedMap().get(percentile);
                String percentileStatus = getStatus(result);
                ConsoleUtil.info("Percentile: {}%%    {}ms (Required: {}ms) - {}",
                        percentile,
                        statistics.getLatencyPercentile(percentile, MILLISECONDS),
                        threshold,
                        percentileStatus);

            }
        }
    }

    /**
     * 输出内存消耗信息
     * @param statistics 统计信息
     * @since 2.0.5
     */
    private void reportMemory(final StatisticsCalculator statistics) {
        // 内存
        ConsoleUtil.info("Memory cost:   {}byte", statistics.getMemory());
    }

    /**
     * 获取状态
     * @param isSuccess 是否成功
     * @return 显示状态字符串
     */
    private String getStatus(boolean isSuccess) {
        if (isSuccess) {
            return StatusEnum.PASSED.getStatus();
        }
        return StatusEnum.FAILED.getStatus();
    }

}
