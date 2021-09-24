package com.ltw.test.controller;

import cn.hutool.core.util.RandomUtil;
import com.ltw.DelayQueueManager;
import com.ltw.test.entity.TestUser;
import com.ltw.test.utils.RedisDelayQueueUtil;
import com.riven.redisson.config.RedissonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    DelayQueueManager delayQueueManager;
    @Autowired
    RedisDelayQueueUtil redisDelayQueueUtil;
    @Autowired
    private RedissonTemplate redissonTemplate;


    @GetMapping
    public void test(){
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static";
        System.out.println(path);

        TestUser testUser = TestUser.builder().id(1L).name("testName").build();
        for(int i=0; i<100; i++){
            int i1 = RandomUtil.randomInt(1, 10);
            HashMap<String, Object> stringIntegerHashMap = new HashMap<>();
            stringIntegerHashMap.put("count", i);
            redissonTemplate.sendWithDelay("test", testUser, stringIntegerHashMap,i1*1000);
        }


    }

    @GetMapping("/testuser")
    public ResponseEntity<TestUser> testuser(){
        TestUser testUser = TestUser.builder().id(123L).idCard("xfvdfv").build();
        return ResponseEntity.ok(testUser);
    }

}
