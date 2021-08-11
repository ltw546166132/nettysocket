package com.ltw.config.web;

import com.ltw.device.standard.DeviceEndpointExporter;
import com.ltw.netty.standard.ServerEndpointExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ProjectWebConfig{
    @Bean
    public DeviceEndpointExporter deviceEndpointExporter() {
        return new DeviceEndpointExporter();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
    private static final int MAX_MESSAGE_SIZE = 20 * 1024;
    private static final long MAX_IDLE = 60 * 60 * 1000;
}
