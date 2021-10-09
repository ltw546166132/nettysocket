package com.ltw.module.test.spring;

import com.ltw.module.test.entity.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
//    @Qualifier("orderService")
    OrderService orderService;

    @Autowired
//    @Qualifier("orderService2")
    OrderService orderService2;

    UserServiceImpl(){}

    @Autowired
    UserServiceImpl(OrderService orderService, OrderService orderService2){
        System.out.println(orderService+"------"+orderService2);
    }

    @Override
    public void testAspect() throws IllegalAccessException {
        TestUser testltw = TestUser.builder().id(123L).name("TESTLTW").idCard("").build();
        for (Field declaredField : testltw.getClass().getDeclaredFields()) {
            String name = declaredField.getName();
            System.out.println(name);
            Class<?> type = declaredField.getType();
            System.out.println(type);
            String s = new String();
            declaredField.setAccessible(true);
            if(type == String.class && declaredField.get(testltw).equals("")){
                declaredField.set(testltw, null);
            }
        }
        System.out.println(testltw);
    }
}
