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

import java.util.Collection;
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
    public void report(Class testClass, Collection<EvaluationContext> evaluationContextSet) {
        for (EvaluationContext context : evaluationContextSet) {

            StatisticsCalculator statistics = context.getStatisticsCalculator();
            EvaluationConfig evaluationConfig = context.getEvaluationConfig();
            EvaluationRequire evaluationRequire = context.getEvaluationRequire();
            EvaluationResult evaluationResult = context.getEvaluationResult();

            String throughputStatus = getStatus(evaluationResult.isTimesPerSecondAchieved());

            infoLog(context, ConsoleUtil.LINE);
            infoLog(context, "Started at:  {}", context.getStartTime());
            infoLog(context, "Invocations:  {}", statistics.getEvaluationCount());
            infoLog(context,"Success:  {}", statistics.getEvaluationCount() - statistics.getErrorCount());
            infoLog(context,"Errors:  {}", statistics.getErrorCount());
            infoLog(context,"Thread Count:  {}", evaluationConfig.getConfigThreads());
            infoLog(context,"Warm up:  {}ms", evaluationConfig.getConfigWarmUp());
            infoLog(context,"Execution time:  {}ms", evaluationConfig.getConfigDuration());
            infoLog(context,"Throughput:  {}/s (Required: {}/s) - {}",
                    evaluationResult.getThroughputQps(),
                    evaluationRequire.getRequireTimesPerSecond(),
                    throughputStatus);
            // 内存
            infoLog(context,"Memory cost:  {}byte", statistics.getMemory());

            infoLog(context,"Min latency:  {}ms (Required: {}ms) - {}",
                    statistics.getMinLatency(MILLISECONDS),
                    evaluationRequire.getRequireMin(),
                    getStatus(evaluationResult.isMinAchieved()));
            infoLog(context,"Max latency:  {}ms (Required: {}ms) - {}",
                    statistics.getMaxLatency(MILLISECONDS),
                    evaluationRequire.getRequireMax(),
                    getStatus(evaluationResult.isMaxAchieved()));
            infoLog(context,"Avg latency:  {}ms (Required: {}ms) - {}",
                    statistics.getMeanLatency(MILLISECONDS),
                    evaluationRequire.getRequireAverage(),
                    getStatus(evaluationResult.isAverageAchieved()));

            for (Map.Entry<Integer, Float> entry : evaluationRequire.getRequirePercentilesMap().entrySet()) {
                Integer percentile = entry.getKey();
                Float threshold = entry.getValue();
                boolean result = evaluationResult.getIsPercentilesAchievedMap().get(percentile);
                String percentileStatus = getStatus(result);
                infoLog(context,"Percentile: {}%%   {}ms (Required: {}ms) - {}",
                        percentile,
                        statistics.getLatencyPercentile(percentile, MILLISECONDS),
                        threshold,
                        percentileStatus);

            }
            infoLog(context, ConsoleUtil.LINE);
        }
    }

    /**
     * 日志输出
     * @param context 上下文
     * @param format 格式化
     * @param args 参数
     * @since 2.0.6
     */
    private void infoLog(final EvaluationContext context,
                     final String format,
                     final Object... args) {
        String className = context.getTestInstance().getClass().getName();
        String methodName = context.getMethodName();

        ConsoleUtil.info(className, methodName, format, args);
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
