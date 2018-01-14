package com.github.houbb.junitperf.core.report.impl;

import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.junitperf.util.FreemarkerUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 网页报告
 * 描述：将统计结果输出HTML页面，打算将此种方式作为默认的报告方式。
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public class HtmlReporter implements Reporter {

    private static final String DEFAULT_REPORT_PATH = System.getProperty("user.dir") + "/target/junitperf/reports/junitperf-report.html";
    private static final String REPORT_TEMPLATE = "/templates/";
    private static final Log log = LogFactory.getLog(ConsoleReporter.class);

    @Override
    public String reportPath() {
        return DEFAULT_REPORT_PATH;
    }

    @Override
    public void report(Set<EvaluationContext> evaluationContextSet) {
        Path outputPath = Paths.get(reportPath());
        try {
            Configuration configuration = FreemarkerUtil.getConfiguration("UTF-8");
            configuration.setClassForTemplateLoading(FreemarkerUtil.class,
                    REPORT_TEMPLATE); //默认

            Template template = configuration.getTemplate("report.ftl");
            Files.createDirectories(outputPath.getParent());
            log.info("Rendering report to: " + outputPath);

            Map<String, Object> root = new HashMap<>();
            root.put("contextData", evaluationContextSet);
            root.put("milliseconds", TimeUnit.MILLISECONDS);
            FreemarkerUtil.createFile(template, reportPath(), root, true);
        } catch (Exception e) {
            log.error("HtmlReporter meet ex: {}", e, e);
        }
    }



}
