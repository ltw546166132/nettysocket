package com.ltw.device.standard;

import cn.hutool.core.thread.GlobalThreadPool;
import com.ltw.device.YCServer;
import com.ltw.device.vo.YCDateVo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class DeviceEndpointExporter extends ApplicationObjectSupport implements SmartInitializingSingleton, BeanFactoryAware {
    @Autowired
    Environment environment;

    private AbstractBeanFactory beanFactory;

    @Autowired
    private YCServer ycServer;
    @Autowired
    private ClimateDeviceDateProcess climateDeviceDateProcess;

    private static BlockingQueue<YCDateVo> bq= new LinkedBlockingQueue<YCDateVo>();

    ExecutorService executor = GlobalThreadPool.getExecutor();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (!(beanFactory instanceof AbstractBeanFactory)) {
            throw new IllegalArgumentException(
                    "AutowiredAnnotationBeanPostProcessor requires a AbstractBeanFactory: " + beanFactory);
        }
        this.beanFactory = (AbstractBeanFactory) beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        registerEndpoints();
        climateDeviceDateProcess.taskRun();
    }

    private void registerEndpoints() {
        ycServer.run();
    }
}
