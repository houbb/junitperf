package com.github.houbb.junitperf.core.apply;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.core.statistics.impl.DefaultStatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.paradise.common.util.DateUtil;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Sets.newHashSet;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/6/28 下午7:33  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class PerfPostProcessor implements TestInstancePostProcessor {

    //region private fields
    /**
     * 统计计算方式
     */
    private final StatisticsCalculator statisticsCalculator;

    /**
     * 报告生成方式
     */
    private final Set<Reporter> reporterSet;

    /**
     * 用于存储上下文
     */
    private static final ConcurrentHashMap<Class, Set<EvaluationContext>> ACTIVE_CONTEXTS = new ConcurrentHashMap<>();
    /**
     * 测试上下文
     */
    private final ExtensionContext context;
    /**
     * 方法信息
     */
    private final Method           method;
    //endregion


    public PerfPostProcessor(ExtensionContext context) {
        this(context, new HtmlReporter());
    }

    public PerfPostProcessor(ExtensionContext context, Reporter... reporters) {
        this.statisticsCalculator = new DefaultStatisticsCalculator();
        this.reporterSet = newHashSet(reporters);
        this.context = context;
        this.method = context.getRequiredTestMethod();
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        JunitPerfConfig perfConfig = method.getAnnotation(JunitPerfConfig.class);
        JunitPerfRequire perfRequire = method.getAnnotation(JunitPerfRequire.class);
        final Class clazz = testInstance.getClass();

        // Group test contexts by test class
        ACTIVE_CONTEXTS.putIfAbsent(clazz, new HashSet<>());

        EvaluationContext evaluationContext = new EvaluationContext(method.getName(),
                DateUtil.getSimpleDateStr());

        evaluationContext.loadConfig(perfConfig);
        evaluationContext.loadRequire(perfRequire);
        ACTIVE_CONTEXTS.get(clazz).add(evaluationContext);

//        new PerformanceEvaluationStatement(evaluationContext,
//                statement,
//                statisticsCalculator,
//                reporterSet,
//                ACTIVE_CONTEXTS.get(description.getTestClass()),
//                description.getTestClass()
//        );
    }

}
