package com.github.houbb.junitperf.model.vo;

import com.github.houbb.junitperf.constant.VersionConstant;

import org.apiguardian.api.API;

/**
 * <p> i18n 对象 </p>
 *
 * <pre> Created: 2018/5/4 下午11:00  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0.2
 * @since 1.0.2
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class I18nVo {

    private String junit_performance_report;
    private String top;
    private String report_created_by;
    private String invocations;
    private String success;
    private String warm_up;
    private String thread_count;
    private String started_at;
    private String execution_time;
    private String type;
    private String actual;
    private String required;
    private String throughput;
    private String min_latency;
    private String max_latency;
    private String avg_latency;

    /**
     * 内存消耗
     * @since 2.0.5
     */
    private String memory;

    public String getJunit_performance_report() {
        return junit_performance_report;
    }

    public void setJunit_performance_report(String junit_performance_report) {
        this.junit_performance_report = junit_performance_report;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getReport_created_by() {
        return report_created_by;
    }

    public void setReport_created_by(String report_created_by) {
        this.report_created_by = report_created_by;
    }

    public String getInvocations() {
        return invocations;
    }

    public void setInvocations(String invocations) {
        this.invocations = invocations;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getWarm_up() {
        return warm_up;
    }

    public void setWarm_up(String warm_up) {
        this.warm_up = warm_up;
    }

    public String getThread_count() {
        return thread_count;
    }

    public void setThread_count(String thread_count) {
        this.thread_count = thread_count;
    }

    public String getStarted_at() {
        return started_at;
    }

    public void setStarted_at(String started_at) {
        this.started_at = started_at;
    }

    public String getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(String execution_time) {
        this.execution_time = execution_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getThroughput() {
        return throughput;
    }

    public void setThroughput(String throughput) {
        this.throughput = throughput;
    }

    public String getMin_latency() {
        return min_latency;
    }

    public void setMin_latency(String min_latency) {
        this.min_latency = min_latency;
    }

    public String getMax_latency() {
        return max_latency;
    }

    public void setMax_latency(String max_latency) {
        this.max_latency = max_latency;
    }

    public String getAvg_latency() {
        return avg_latency;
    }

    public void setAvg_latency(String avg_latency) {
        this.avg_latency = avg_latency;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
