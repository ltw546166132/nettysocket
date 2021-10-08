package com.ltw.module.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.ltw.module.test.spring")
@EnableAspectJAutoProxy
public class MyConfig {

}
