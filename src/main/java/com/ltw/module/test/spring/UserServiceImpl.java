package com.ltw.module.test.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    @Qualifier("orderService")
    OrderService orderService;

    @Autowired
    @Qualifier("orderService2")
    OrderService orderService2;

    UserServiceImpl(){}

    @Autowired
    UserServiceImpl(OrderService orderService, OrderService orderService2){
        System.out.println(orderService+"------"+orderService2);
    }
}
