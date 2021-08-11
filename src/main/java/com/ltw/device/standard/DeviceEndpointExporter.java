package com.ltw.device.standard;

import com.ltw.device.YCServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;

public class DeviceEndpointExporter extends ApplicationObjectSupport implements SmartInitializingSingleton, BeanFactoryAware {
    @Autowired
    Environment environment;

    private AbstractBeanFactory beanFactory;

    @Autowired
    private YCServer ycServer;

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
    }

    private void registerEndpoints() {
        ycServer.run();
    }
}
