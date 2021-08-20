package com.ltw.device.listener;

import cn.hutool.core.util.ObjectUtil;
import com.ltw.device.event.DeviceDateEvent;
import com.ltw.device.service.JHKJDevice;
import com.ltw.device.vo.YCDateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClimateDeviceEventListener {
    @Autowired
    JHKJDevice jhkjDevice;

    @EventListener(value = DeviceDateEvent.class)
    @Async
    public void executeYCDeviceDate(DeviceDateEvent deviceDateEvent){
        String msg = deviceDateEvent.getMsg();
        YCDateVo ycDateVo = jhkjDevice.parseData(msg);
        if(ObjectUtil.isNotNull(ycDateVo)){
            jhkjDevice.executeData(ycDateVo);
        }

    }
}
