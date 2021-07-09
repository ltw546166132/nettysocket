package com.ltw;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.LinkedList;
import java.util.function.Consumer;

public class Test {
    public static void main(String[] args) {
        LinkedList<Consumer> testConsumers = new LinkedList<>();
        for(int i = 0; i<5; i++){
            TestConsumer testConsumer = new TestConsumer(RandomUtil.randomString(5));
            Consumer<String> consumer = testConsumer.getConsumer();
            testConsumers.add(consumer);

        }
        for (Consumer consumer: testConsumers) {
            consumer.accept(RandomUtil.randomString(3));
        }

    }
}
