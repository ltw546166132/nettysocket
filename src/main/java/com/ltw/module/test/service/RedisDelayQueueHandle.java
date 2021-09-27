package com.ltw.module.test.service;

public interface RedisDelayQueueHandle<T> {
    void execute(T t);
}
