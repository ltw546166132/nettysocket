package com.ltw.device.standard;

import cn.hutool.core.thread.GlobalThreadPool;
import com.ltw.device.vo.YCDateVo;
import com.ltw.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ClimateDeviceDateProcess {
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static BlockingQueue<YCDateVo> bq= new LinkedBlockingQueue<YCDateVo>();

    public Boolean offerQueue(YCDateVo ycDateVo){
        return bq.offer(ycDateVo);
    }

    ExecutorService executor = GlobalThreadPool.getExecutor();

    private Boolean isStop = Boolean.TRUE;
    public void taskRun(){
        if(isStop){
            System.out.println("队列轮询开始");
            isStop = Boolean.FALSE;
            executor.execute(() -> {
                while(true){
                    try {
                        YCDateVo take = bq.take();
                        System.out.println("队列中的YCDateVo--->"+take.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //将队列里的扬尘数据发到websocket页面
    private void executeWebSocketMsg(YCDateVo ycDateVo){

    }
}
