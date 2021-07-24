package com.github.houbb.junitperf.core.jupiter.context;

import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.junitperf.support.exception.JunitPerfRuntimeException;
import com.github.houbb.junitperf.support.statements.PerformanceEvaluationStatement;

import org.apiguardian.api.API;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 配置上下文 </p>
 *
 * <pre> Created: 2018/6/28 下午5:01  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 2.0.0
 * @since 2.0.0
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class PerfConfigContext implements TestTemplateInvocationContext {

    /**
     * 用于存储上下文
     */
    private static final ConcurrentHashMap<Class, List<EvaluationContext>> ACTIVE_CONTEXTS = new ConcurrentHashMap<>();
    private final        Method                                           method;
    private              JunitPerfConfig                                  perfConfig;
    private              JunitPerfRequire                                 perfRequire;


    public PerfConfigContext(ExtensionContext context) {
        this.method = context.getRequiredTestMethod();
        this.perfConfig = method.getAnnotation(JunitPerfConfig.class);
        this.perfRequire = method.getAnnotation(JunitPerfRequire.class);
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        return Collections.singletonList(
                (TestInstancePostProcessor) (testInstance, context) -> {
                    final Class<?> clazz = testInstance.getClass();
                    // Group test contexts by test class
                    ACTIVE_CONTEXTS.putIfAbsent(clazz, new ArrayList<>());

                    EvaluationContext evaluationContext = new EvaluationContext(testInstance,
                            method,
                            DateUtil.getCurrentDateTimeStr());
                    evaluationContext.loadConfig(perfConfig);
                    evaluationContext.loadRequire(perfRequire);
                    StatisticsCalculator statisticsCalculator = perfConfig.statistics().newInstance();
                    Set<Reporter> reporterSet = getReporterSet();
                    ACTIVE_CONTEXTS.get(clazz).add(evaluationContext);
                    try {
                        new PerformanceEvaluationStatement(evaluationContext,
                                statisticsCalculator,
                                reporterSet,
                                ACTIVE_CONTEXTS.get(clazz),
                                clazz).evaluate();
                    } catch (Throwable throwable) {
                        throw new JunitPerfRuntimeException(throwable);
                    }
                }
        );
    }

    /**
     * 获取报告集合
     *
     * @return 报告集合
     */
    private Set<Reporter> getReporterSet() {
        Set<Reporter> reporterSet = new HashSet<>();
        Class<? extends Reporter>[] reporters = perfConfig.reporter();
        for (Class clazz : reporters) {
            try {
                Reporter reporter = (Reporter) clazz.newInstance();
                reporterSet.add(reporter);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new JunitPerfRuntimeException(e);
            }
        }
        return reporterSet;
    }

}
