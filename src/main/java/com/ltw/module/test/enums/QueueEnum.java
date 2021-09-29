package com.ltw.module.test.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

    private static Map<String, QueueEnum> map = new HashMap<>();

    public static String getDesByCode(String code) {
        if(contains(code)) {
            return map.get(code).getName();
        }
        return "";
    }

    public static boolean contains(String code) {
        return map.containsKey(code);
    }
}
