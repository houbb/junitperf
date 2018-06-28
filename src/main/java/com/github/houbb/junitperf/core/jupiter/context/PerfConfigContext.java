package com.github.houbb.junitperf.core.jupiter.context;

import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.paradise.common.util.ObjectUtil;

import org.apiguardian.api.API;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/6/28 下午5:01  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class PerfConfigContext implements TestTemplateInvocationContext {

    private final ExtensionContext context;
    private final Method method;

    public PerfConfigContext(ExtensionContext context) {
        this.context = context;
        this.method = context.getRequiredTestMethod();
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        return Collections.singletonList(
                new TestInstancePostProcessor() {
                    @Override
                    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
//                        Optional optional = getParameters();
//
//
//                        method.invoke(testInstance);
//                        System.out.println(testInstance);
                    }
                }
        );
    }



    /**
     * 获取参数列表
     * @return 参数列表
     */
    private Optional<Parameter[]> getParameters() {
        Parameter[] parameters = method.getParameters();
        if(ObjectUtil.isEmpty(parameters)) {
            return Optional.empty();
        }
        return Optional.of(parameters);
    }

}
