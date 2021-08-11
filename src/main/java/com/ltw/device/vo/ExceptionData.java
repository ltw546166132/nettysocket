package com.ltw.device.vo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ExceptionData {
    private Integer pointId;
    private String pointName;
    private Date qn;
    private String mn;
    private Integer enterpriseId;
    private String enterpriseName;
    private String dataType;
    private Map<String, Map<String, String>> data;
}
