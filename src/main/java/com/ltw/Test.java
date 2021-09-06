package com.ltw;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.thread.GlobalThreadPool;
import com.ltw.test.config.MyConfig;
import com.ltw.test.config.MyLog;
import com.ltw.test.entity.TestUser;
import com.ltw.test.enums.TestFunctionEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;

@Slf4j
public class Test {
    static ExecutorService executor = GlobalThreadPool.getExecutor();
    private static DelayQueue delayQueue  = new DelayQueue();

    public static void main(String[] args) throws InterruptedException {
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


        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        MyLog bean = context.getBean(MyLog.class);


        TestUser build = TestUser.builder().idCard("430102201003072712").build();
        TestUser build1 = TestUser.builder().idCard("430102199003070936").build();
        TestUser build2 = TestUser.builder().idCard("430102199003075390").build();
        List<TestUser> list = CollectionUtil.list(false);
        list.add(build2);
        list.add(build1);
        list.add(build);
        Integer num = (Integer) TestFunctionEnums.AGE20.getGetCountNum().apply(list);
        System.out.println(num);

    }

}
