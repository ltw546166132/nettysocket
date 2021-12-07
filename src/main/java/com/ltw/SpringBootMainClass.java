package com.ltw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.yeauty.annotation.EnableWebSocket;

import java.io.File;

@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainClass.class, args);
        System.out.println(new File("./src/main/resources/VzLPRSDK.dll").getAbsolutePath());
    }
}
