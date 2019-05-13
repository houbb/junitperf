package com.github.houbb.junitperf.util;

import com.github.houbb.junitperf.support.exception.JunitPerfException;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;

import java.io.*;
import java.util.Locale;
import java.util.Map;

/**
 * 2017/11/13
 *
 * Freemarker 工具类
 * @author houbinbin
 * @version 1.0
 */
public final class FreemarkerUtil {

    private FreemarkerUtil(){}

    /**    
     * 日志    
     */    
    private static final Log log = LogFactory.getLog(FreemarkerUtil.class);

    /**    
     * 组态    
     */    
    private static Configuration configuration = null;

    /**
     * 获取配置
     * @param encoding 编码
     * @return 配置
     */
    public static Configuration getConfiguration(String encoding) {
            return getConfiguration(encoding, true);
    }

    /**
     * define Configuration
     * @param encoding 编码
     * @param isForce 是否强制
     * @return 配置
     */
    public static Configuration getConfiguration(String encoding, boolean isForce) {
        if (configuration == null
                || isForce) {
            configuration = new Configuration();
            configuration.setEncoding(Locale.getDefault(), encoding);    //编码

            //// 设置对象的包装器
            configuration.setObjectWrapper(new DefaultObjectWrapper());

            // 设置异常处理器//这样的话就可以${a.b.c.d}即使没有属性也不会出错
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

            //默认 FTL map 中不支持非 String 的 key
            configuration.setObjectWrapper(new BeansWrapper());
        }

        return configuration;
    }

    /**
     * create file by template, htmlName, and modal map;
     * 1. 不存在直接创建
     * 2. 存在 + 覆盖：则删除直接覆盖
     * @param template 模板信息
     * @param targetFilePath 目标路径
     * @param map 配置属性
     * @param isOverwriteWhenExists 是否覆盖
     * @return 是否创建成功
     * @throws JunitPerfException if any
     * @throws IOException if any
     */
    public static boolean createFile(Template template, String targetFilePath, Map<String, Object> map, boolean isOverwriteWhenExists)
            throws JunitPerfException, IOException {
        boolean result = true;
        File file = new File(targetFilePath);

        //create parent dir first.
        boolean makeDirs = file.getParentFile().mkdirs();
        log.debug("create file makeDirs:{}", makeDirs);

        if (!file.exists()) {
            result = file.createNewFile();
            flushFileContent(template, map, file);
        } else if(file.exists()
                && isOverwriteWhenExists) {
            flushFileContent(template, map, file);
        } else {
            //ignore
        }

        return result;
    }

    /**
     * 刷新文件内容
     * @param template 模板
     * @param map 配置
     * @param file 文件
     */
    private static void flushFileContent(Template template, Map<String, Object> map, File file)
            throws JunitPerfException {
        try {
            Writer out = new BufferedWriter(new FileWriter(file));
            template.process(map, out);
            out.flush();
        } catch (IOException | TemplateException e) {
            throw new JunitPerfException(e);
        }
    }

}
