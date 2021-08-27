package com.ltw.test.service;

import java.util.concurrent.TimeUnit;

public interface DelayQueueService<T> {
    /**
     * 推送数据
     *
     * @param data
     * @param queueName
     */
    void pushData(T data, String queueName);

    /**
     * 推送数据
     *
     * @param data
     * @param time
     * @param timeUnit
     * @param queueName
     */
    void pushData(T data, long time, TimeUnit timeUnit, String queueName);

    /**
     * 拉取数据
     *
     * @param queueName
     * @return
     */
    T pullData(String queueName);
}
