package com.ltw.device.vo;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class YCDateVo {
    private String deviceNum;
    private LocalDateTime dateSendTime;
    private String projectId;
    private String questNum;
    //温度
    private String temperature;
    //湿度
    private String humidity;
    //PM2.5
    private String pm;
    //PM10
    private String pm10;
    //TSP颗粒物
    private String tsp;
    //噪音
    private String noise;
    //风速
    private String airspeed;
    //气压
    private String airpressure;
    //风向
    private String airdirection;
    //风力
    private String airpower;
}
