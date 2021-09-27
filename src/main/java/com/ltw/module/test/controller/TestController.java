package com.ltw.module.test.controller;

import cn.hutool.core.util.RandomUtil;
import com.ltw.DelayQueueManager;
import com.ltw.common.api.CommonResult;
import com.ltw.module.test.entity.TestUser;
import com.ltw.module.test.utils.RedisDelayQueueUtil;
import com.riven.redisson.config.RedissonTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Api(value = "测试Controller")
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    DelayQueueManager delayQueueManager;
    @Autowired
    RedisDelayQueueUtil redisDelayQueueUtil;
    @Autowired
    private RedissonTemplate redissonTemplate;
    @Resource
    private RedisTemplate<String, Long> redisTemplate;


    @ApiOperation(value = "testController")
    @GetMapping
    public void test(){
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static";
        System.out.println(path);
        redisTemplate.opsForValue().setIfAbsent("testRedisKey", 123L, 20, TimeUnit.SECONDS);
        TestUser testUser = TestUser.builder().id(1L).name("testName").build();
        for(int i=0; i<10; i++){
            int i1 = RandomUtil.randomInt(1, 10);
            HashMap<String, Object> stringIntegerHashMap = new HashMap<>();
            stringIntegerHashMap.put("count", i);
            redissonTemplate.sendWithDelay("testRedissonQueue", testUser, stringIntegerHashMap,i1*1000);
        }
    }

    @GetMapping("/testuser")
    public ResponseEntity<TestUser> testuser(){
        TestUser testUser = TestUser.builder().id(123L).idCard("xfvdfv").build();
        return ResponseEntity.ok(testUser);
    }

    @GetMapping("/orgtree")
    public CommonResult<Boolean> orgtree(){

        return null;
    }

    @PostMapping("addorg")
    public CommonResult<Boolean> addorg(){

        return null;
    }
}
