package com.ltw;

import java.util.function.Consumer;

public class TestConsumer {


    public String showtext = "123456";

    public TestConsumer(String showtext){
        this.showtext = showtext;
    }


    public Consumer<String> consumer = v -> {
        System.out.println(showtext+"--"+v);
    };




    public Consumer<String> getConsumer(){
        return consumer;
    }

}
