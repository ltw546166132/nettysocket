package com.ltw.device.common.enums;

import cn.hutool.core.map.MapUtil;

import java.util.Map;

/**
 * @author libawall
 */

public enum YCEnum {
    temperature("a01001","温度"),humidity("a01002","湿度"),pm("a34004","PM2.5"),pm10("a34002","PM10"),tsp("a34001","TSP颗粒物"),noise("LA","噪音"),airspeed("a01007","风速"),
    airpressure("a01006","气压"),airdirection("a01008","风向");
    private String code;
    private String desc;
    YCEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, YCEnum> map = MapUtil.newHashMap();
    static{
        for (YCEnum item: YCEnum.values()) {
            map.put(item.code, item);
        }
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static YCEnum getEnumByCode(String code){
        return map.get(code);
    }

    public static boolean contains(String code){
        return map.containsKey(code);
    }
}
