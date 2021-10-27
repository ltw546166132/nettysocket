package com.ltw.device.common.constans;

import org.yeauty.pojo.Session;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ltw
 */
public class ClimateDeviceSocketSession {
    /**
     * key为channelId
     */
    public static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
    /**
     * key为projectId   value为channelId
     */
    public static ConcurrentHashMap<String, List<String>> projectChannelMap = new ConcurrentHashMap<>();

    /**
     * key为channelId  value为projectId
     */
    public static ConcurrentHashMap<String, String> channelProjectMap = new ConcurrentHashMap<>();

}
