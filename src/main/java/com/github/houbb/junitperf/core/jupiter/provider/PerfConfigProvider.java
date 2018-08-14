package com.github.houbb.junitperf.core.jupiter.provider;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.jupiter.context.PerfConfigContext;

import org.apiguardian.api.API;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.stream.Stream;

/**
 * <p> 配置实现 </p>
 *
 * <pre> Created: 2018/6/28 下午5:53  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
@API(status = API.Status.INTERNAL)
public class PerfConfigProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod()
                .filter(m -> AnnotationSupport.isAnnotated(m, JunitPerfConfig.class))
                .isPresent();
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(new PerfConfigContext(context));
    }

}
