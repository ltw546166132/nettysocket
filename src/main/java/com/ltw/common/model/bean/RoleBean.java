package com.ltw.common.model.bean;

import lombok.Data;

import java.util.List;

@Data
public class RoleBean {
    private Long id;
    private String rolename;
    private List<ResourceBean> resourceBeanList;
}
