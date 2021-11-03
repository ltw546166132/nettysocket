package com.ltw;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson.JSON;
import com.ltw.module.test.MyConfig;
import com.ltw.module.test.model.entity.TestUser;
import com.ltw.module.test.enums.TestFunctionEnums;
import com.ltw.module.test.model.entity.User;
import com.ltw.module.test.spring.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Slf4j
public class Test {
    static ExecutorService executor = GlobalThreadPool.getExecutor();
    private static DelayQueue delayQueue  = new DelayQueue();

    public static void main(String[] args) {
        log.info("------>"+new Random().nextInt(555)+"");
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

//        ArrayList<TestUser> testUsers = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            int i1 = RandomUtil.randomInt(1, 20);
//            TestUser build = TestUser.builder().id(Long.parseLong(i1 + "")).name(i1 + "").build();
//            testUsers.add(build);
//        }
//        List<TestUser> sort = CollectionUtil.sort(testUsers, (o1, o2) -> {
//            Long id = o1.getId();
//            Long id1 = o2.getId();
//            if (id < id1) {
//                return -1;
//            } else if (id > id1) {
//                return 1;
//            }
//            return 0;
//        });
//        sort.forEach(testUser -> System.out.println(testUser.getId()));


//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
//        UserService userService = context.getBean(UserService.class);
//        log.info(JSON.toJSONString(userService));


//        try {
//            userService.testAspect();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }


        TestUser build = TestUser.builder().id(1L).idCard("430102201003072712").build();
        TestUser build1 = TestUser.builder().id(2L).idCard("430102199003070936").build();
        TestUser build2 = TestUser.builder().id(3L).idCard("430102199003075390").build();
        List<TestUser> list = CollectionUtil.list(true);
        list.add(build);
        list.add(build1);
        list.add(build2);
//        List<TestUser> collect = list.stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).collect(Collectors.toList());

        List<TestUser> collect1 = list.stream().sorted(Comparator.comparing(TestUser::getId).reversed()).collect(Collectors.toList());
        System.out.println(list);


        Integer num = (Integer) TestFunctionEnums.AGE20.getGetCountNum().apply(list);
        System.out.println(num);


        DateTime birthDate = IdcardUtil.getBirthDate("340621199812264428");
        System.out.println(birthDate.year());
        System.out.println(birthDate.month());
        System.out.println(birthDate.dayOfMonth());
        Date date = birthDate.toJdkDate();
        System.out.println(date);
    }

}
