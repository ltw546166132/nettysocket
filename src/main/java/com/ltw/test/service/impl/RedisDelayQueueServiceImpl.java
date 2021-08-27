package com.ltw.test.service.impl;

import com.ltw.test.service.DelayQueueService;
import com.ltw.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisDelayQueueServiceImpl<T> implements DelayQueueService<T> {
    private static RedissonClient redissonClient;

    static {
        redissonClient = SpringUtils.getBean(RedissonClient.class);


    }

    private final String queueName;
    private RBlockingQueue<T> blockingQueue;
    private RDelayedQueue<T> delayedQueue;

    public RedisDelayQueueServiceImpl(String queueName) {
        this.queueName = queueName;
        this.blockingQueue = getBlockingQueue();
        this.delayedQueue = getDelayQueue(blockingQueue);
    }

    /**
     * 获取阻塞队列
     *
     * @return
     */
    private RBlockingQueue<T> getBlockingQueue() {
        return redissonClient.getBlockingQueue(queueName);
    }

    /**
     * 获取延迟队列
     *
     * @param blockingQueue
     * @return
     */
    private RDelayedQueue<T> getDelayQueue(RBlockingQueue<T> blockingQueue) {
        return redissonClient.getDelayedQueue(blockingQueue);
    }

    /**
     * 推送数据
     *
     * @param data
     * @param queueName
     */
    @Override
    public void pushData(T data, String queueName) {
        pushData(data, 0, TimeUnit.MILLISECONDS, queueName);
    }

    /**
     * 推送数据
     *
     * @param data
     * @param time
     * @param timeUnit
     * @param queueName
     */
    @Override
    public void pushData(T data, long time, TimeUnit timeUnit, String queueName) {
        delayedQueue.offerAsync(data, time < 0 ? 0 : time, timeUnit);
    }

    /**
     * 拉取数据
     *
     * @param queueName
     * @return
     */
    @Override
    public T pullData(String queueName) {

        T currentData = null;
        try {
            currentData = blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        log.info("获取到的数据 : {}", currentData);

        return currentData;
    }
}
