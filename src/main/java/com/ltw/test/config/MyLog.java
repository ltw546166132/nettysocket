package com.ltw.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyLog {
    @Value(value = "server.port")
    private String flag;

    @Override
    public String toString() {
        return "MyLog{ flag="+ flag+"}";
    }

    public MyLog(){
        System.out.println(toString());
    }
}
