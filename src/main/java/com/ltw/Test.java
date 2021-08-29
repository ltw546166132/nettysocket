package com.ltw;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

@Slf4j
public class Test {
    static ExecutorService executor = GlobalThreadPool.getExecutor();
    private static DelayQueue delayQueue  = new DelayQueue();

    public static void main(String[] args) throws InterruptedException {
//        LinkedList<Consumer> testConsumers = new LinkedList<>();
//        for(int i = 0; i<5; i++){
//            TestConsumer testConsumer = new TestConsumer(RandomUtil.randomString(5));
//            Consumer<String> consumer = testConsumer.getConsumer();
//            testConsumers.add(consumer);
//
//        }
//        for (Consumer consumer: testConsumers) {
//            consumer.accept(RandomUtil.randomString(3));
//        }
String a = "1234567890";
        String substring = a.substring(0, 5);
        log.info(substring);
        log.info(a.length()+"");


    }
}
