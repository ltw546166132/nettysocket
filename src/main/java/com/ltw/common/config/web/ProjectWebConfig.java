package com.ltw.common.config.web;

import com.ltw.device.standard.DeviceEndpointExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.yeauty.standard.ServerEndpointExporter;

@Configuration
@EnableWebMvc
public class ProjectWebConfig{
    @Bean
    public DeviceEndpointExporter deviceEndpointExporter() {
        return new DeviceEndpointExporter();
    }


}
