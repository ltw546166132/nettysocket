package com.ltw.module.test.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.ltw.DelayQueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    DelayQueueManager delayQueueManager;

    @GetMapping
    public void test(){
        for(int i=0; i<7; i++){
            putout();
        }
    }

    private void putout(){
        int i = RandomUtil.randomInt(1, 11);
        UUID uuid = UUID.fastUUID();
        System.out.println("uuid"+uuid+"开始时间:"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"延迟"+i+"秒");
        delayQueueManager.putDelayQueue(() -> {
            System.out.println("uuid"+uuid+"执行时间:"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }, i, TimeUnit.SECONDS);
    }
}
