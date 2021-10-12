package com.ltw.common.model.bean;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserBean {
    private Long id;
    private String username;
    private String password;
    private List<RoleBean> roleBeanList = new ArrayList<>();
    private List<ResourceBean> resourceBeanList = new ArrayList<>();

    public Boolean havePermission(String resource){
        return this.resourceBeanList.stream().filter(resourceBean -> resourceBean.getResourcename().equals(resource)).count()>0;
    }
}
