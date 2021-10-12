package com.ltw.module.test.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.ltw.DelayQueueManager;
import com.ltw.common.api.CommonResult;
import com.ltw.module.test.component.CancelOrderSender;
import com.ltw.module.test.model.entity.Org;
import com.ltw.module.test.model.entity.TestUser;
import com.ltw.module.test.service.OrgService;
import com.ltw.module.test.utils.RedisDelayQueueUtil;
import com.riven.redisson.config.RedissonTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(value = "测试Controller")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    DelayQueueManager delayQueueManager;
    @Autowired
    RedisDelayQueueUtil redisDelayQueueUtil;
    @Autowired
    private RedissonTemplate redissonTemplate;
    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Autowired
    private OrgService orgService;
    @Autowired
    private CancelOrderSender cancelOrderSender;

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
    public CommonResult<List<Tree<Long>>> orgtree(){
        List<Tree<Long>> tree = orgService.tree();
        return CommonResult.success(tree);
    }

    @GetMapping("/addorg")
    public CommonResult<Boolean> addorg(@RequestParam("parentId") Long parentId){
        Org org = new Org();
        org.setParentId(parentId);
        org.setOrgName("testOrg-"+IdUtil.simpleUUID());
        boolean save = orgService.save(org);
        return CommonResult.success(save);
    }

    @GetMapping("/rabbitmq")
    public void testRabbitMq(){
        log.info("发送时间:  "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        cancelOrderSender.sendMessage(1L, 3000);
    }

    @GetMapping("/fanout")
    public void fanout(){
        List<Org> list = orgService.list();
        log.info("发送时间:  "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        cancelOrderSender.sendMessageFanout(list);
    }

}
