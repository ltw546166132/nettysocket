package com.ltw.module.test.lifter;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

public class Mem {
	
	
    public static Map<String, Channel> addressChannelTOMap = new ConcurrentHashMap<String, Channel>();//保存 和设备建立的socket
    
    public static Map<String, Channel> addressClientChannelTOMap = new ConcurrentHashMap<String, Channel>();//保存 和设备建立的socket
    
    /*address和uuid*/
    public static Map<String, String> addressUUIDMap = new ConcurrentHashMap<String, String>();//保存 和设备建立的socket
    
    public static Bootstrap bootstrap  = new Bootstrap();

    public static Map<String, String> deviceAddressMap = new ConcurrentHashMap<String, String>();//保存 和设备建立的socket

    public static Map<String, DeviceHeight> deviceHeightMap = new ConcurrentHashMap<String, DeviceHeight>();//保存 和设备建立的socket

    public static final String imagePath = "/mnt/data1/image/";//图片路径

    public static final String imagePathHoist = imagePath + "hoistDeviceImage/";//升降机图片路径

    private static Date nowDate = new Date();

    private static Map<String, Date> latestOnlineTime = new ConcurrentHashMap<String, Date>();//设备最近一次在线时间

    public static Map<String, String> addressToMsg = new ConcurrentHashMap<String, String>();
    
    public static Map<String,Set<String>> clientDeviceMap = new ConcurrentHashMap<String,Set<String>>();
    
    public static Map<String, Date> getLatestOnlineTime() {
        return latestOnlineTime;
    }

    public static void setNowDate(Date nowDate) {
        Mem.nowDate = nowDate;
    }

    public static Date getNowDate() {
        return nowDate;
    }

    public static Map<String, Byte> listRtmrvtMap = new ConcurrentHashMap<String, Byte>();

    public static Map<String, Date> onlineMap = new ConcurrentHashMap<String, Date>();

}
