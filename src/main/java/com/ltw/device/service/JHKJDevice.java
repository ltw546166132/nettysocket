package com.ltw.device.service;

import com.ltw.device.vo.YCDateVo;

/**
 * @author ltw
 */
public interface JHKJDevice {
    YCDateVo parseData(String msg);
    void executeData(YCDateVo ycDateVo);
}
