package com.ltw;

import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class DelayQueueManager implements CommandLineRunner {
    @Autowired
    private RedissonClient redissonClient;

    private final static int DEFAULT_THREAD_NUM = 5;
    private static int thread_num = DEFAULT_THREAD_NUM;
    // 固定大小线程池
    private ExecutorService executor;
    // 守护线程
//    private Thread daemonThread;
    // 延时队列
    private DelayQueue<BaseDelayedTask<?>> delayQueue;
    private static final AtomicLong atomic = new AtomicLong(0);
    private final long n = 1;
    private static DelayQueueManager instance = new DelayQueueManager();
    private RDelayedQueue<BaseDelayedTask<?>> rdeque;


    /**
     * 初始化
     */
    public void init() {
//        RQueue<BaseDelayedTask<?>> message = redissonClient.getQueue("message");
//        rdeque = redissonClient.getDelayedQueue(message);
        executor = GlobalThreadPool.getExecutor();
        executor.execute(() -> {
            execute();
        });
    }

    private void execute() {
        while (true) {
            Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
            System.out.println("当前存活线程数量:" + map.size());
            int taskNum = delayQueue.size();
            System.out.println("当前延时任务数量:" + taskNum);
            try {
                // 从延时队列中获取任务
                BaseDelayedTask<?> delayOrderTask = delayQueue.take();
                if (delayOrderTask != null) {
                    Runnable task = delayOrderTask.getTask();
                    if (null == task) {
                        continue;
                    }
                    // 提交到线程池执行task
                    executor.execute(task);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加任务
     *
     * @param task
     * @param time
     *            延时时间
     * @param unit
     *            时间单位
     */
    public void putDelayQueue(Runnable task, long time, TimeUnit unit) {
        putDelayQueue(null, task, time, unit);
    }

    /**
     * 添加任务
     *
     * @param task
     * @param time
     *            延时时间
     * @param unit
     *            时间单位
     */
    public void putDelayQueue(String Identifiers, Runnable task, long time, TimeUnit unit) {
        // 获取延时时间
        long timeout = TimeUnit.MILLISECONDS.convert(time, unit);
        // 将任务封装成实现Delayed接口的消息体
        BaseDelayedTask<?> delayOrder = null;
        if(StrUtil.isNotBlank(Identifiers)){
             delayOrder = new BaseDelayedTask<>(Identifiers, timeout, task);
        }else{
             delayOrder = new BaseDelayedTask<>(timeout, task);
        }
        // 将消息体放到延时队列中
        rdeque.offer(delayOrder);
    }

    /**
     * 删除任务
     *
     * @param id
     * @return
     */
    public boolean removeTask(String id) {

        return delayQueue.remove(new BaseDelayedTask<>(id));
    }

    @Override
    public void run(String... args) throws Exception {
        delayQueue = new DelayQueue<>();
        init();
    }
}
