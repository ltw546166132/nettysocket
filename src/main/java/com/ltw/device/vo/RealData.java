package com.ltw.device.vo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class RealData {
    private Integer pointId;
    private String mn;
    private Date qn;
    private Map<String, Map<String, Map<String, String>>> data;
}
