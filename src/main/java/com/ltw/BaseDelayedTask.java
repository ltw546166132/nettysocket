package com.ltw;

import cn.hutool.core.util.StrUtil;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class BaseDelayedTask<T extends Runnable> implements Delayed {
    /**
     * 延迟的时间（毫秒）
     */
    private long time ;
    private T task;
    private String id;

    public BaseDelayedTask(String id) {
        this.id = id;
    }

    public BaseDelayedTask(long time, T task) {
        this.time = System.currentTimeMillis() + time;
        this.task = task;
    }

    public BaseDelayedTask(String id, long time, T task) {
        this.id = id;
        this.time = System.currentTimeMillis() + time;
        this.task = task;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseDelayedTask) {
            if(StrUtil.isNotBlank(((BaseDelayedTask<?>) obj).getId()) && StrUtil.isNotBlank(this.id)){
                return this.id.equals(((BaseDelayedTask<?>) obj).getId());
            }
        }
        return false;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public T getTask() {
        return task;
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Delayed o) {
        BaseDelayedTask o1 = (BaseDelayedTask) o;
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

}
