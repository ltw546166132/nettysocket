package com.ltw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.yeauty.annotation.EnableWebSocket;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableWebSocket
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainClass.class, args);
    }
}
