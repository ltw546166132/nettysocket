package com.ltw.test.service;

public interface RedisDelayQueueHandle<T> {
    void execute(T t);
}
