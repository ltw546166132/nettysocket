package com.ltw.module.test.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect {

    @Before("execution(public void com.ltw.module.test.spring.UserService.testAspect())")
    public void testExecution(JoinPoint joinPoint){
        System.out.println("@Before");
    }
}
