package com.ltw.test.service.impl;

import com.ltw.test.service.RedisDelayQueueHandle;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderTimeoutNotEvaluated implements RedisDelayQueueHandle<Map> {
    @Override
    public void execute(Map map) {

    }
}
