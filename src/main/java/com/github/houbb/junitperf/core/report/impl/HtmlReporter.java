package com.github.houbb.junitperf.core.report.impl;

import com.github.houbb.heaven.util.lang.ConsoleUtil;
import com.github.houbb.heaven.util.nio.PathUtil;
import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.junitperf.support.i18n.I18N;
import com.github.houbb.junitperf.util.FreemarkerUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

import org.apiguardian.api.API;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 网页报告
 * 描述：将统计结果输出HTML页面，打算将此种方式作为默认的报告方式。
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class HtmlReporter implements Reporter {

    /**
     * 默认输出文件夹
     */
    private static final String DEFAULT_REPORT_PACKAGE = System.getProperty("user.dir") + "/target/junitperf/reports/";
    /**
     * 模板文件夹
     */
    private static final String REPORT_TEMPLATE = "/templates/";

    @Override
    public void report(Class testClass, Collection<EvaluationContext> evaluationContextSet) {
        Path outputPath = Paths.get(DEFAULT_REPORT_PACKAGE + PathUtil.packageToPath(testClass.getName())+".html");
        try {
            Configuration configuration = FreemarkerUtil.getConfiguration("UTF-8");
            configuration.setClassForTemplateLoading(FreemarkerUtil.class,
                    REPORT_TEMPLATE);

            Template template = configuration.getTemplate("report.ftl");
            Files.createDirectories(outputPath.getParent());
            ConsoleUtil.info("Rendering report to: " + outputPath);

            Map<String, Object> root = new HashMap<>();
            root.put("className", testClass.getSimpleName());
            root.put("contextData", evaluationContextSet);
            root.put("milliseconds", TimeUnit.MILLISECONDS);
            root.put("i18n", I18N.buildI18nVo());
            FreemarkerUtil.createFile(template, outputPath.toString(), root, true);
        } catch (Exception e) {
            ConsoleUtil.info("HtmlReporter meet ex: {}", e, e);
        }
    }

}
