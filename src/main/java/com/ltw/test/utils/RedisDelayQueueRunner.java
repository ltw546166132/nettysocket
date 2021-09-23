package com.ltw.test.utils;

import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.extra.spring.SpringUtil;
import com.ltw.test.enums.RedisDelayQueueEnum;
import com.ltw.test.service.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisDelayQueueRunner implements CommandLineRunner {
    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Override
    public void run(String... args) {
        RedisDelayQueueEnum[] queueEnums = RedisDelayQueueEnum.values();
        for (RedisDelayQueueEnum queueEnum : queueEnums) {
            GlobalThreadPool.getExecutor().execute(() -> {
                Object value = null;
                while (true){
                    try {
                        value = redisDelayQueueUtil.getDelayQueue(queueEnum.getCode());
                        if (value != null) {
                            RedisDelayQueueHandle redisDelayQueueHandle = SpringUtil.getBean(queueEnum.getBeanId());
                            redisDelayQueueHandle.execute(value);
                        }
                    } catch (InterruptedException e) {
                        log.error("(Redis延迟队列异常中断) {}", e.getMessage());
                    }
                }
            });
        }
        log.info("Redis延迟队列启动成功");
    }
}
