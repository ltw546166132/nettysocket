package com.ltw.device.event;

import com.ltw.device.vo.YCDateVo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author ltw
 */
@Getter
public class DeviceDateEvent extends ApplicationEvent {
    private String msg;
    public DeviceDateEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }
}
