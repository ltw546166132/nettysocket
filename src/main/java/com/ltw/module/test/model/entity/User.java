package com.ltw.module.test.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ltw.common.model.domain.BaseEntity;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@TableName(value = "user")
@Data
public class User extends BaseEntity {
    private Long id;
    private String username;
    private String password;
    private String account;


    @TableField(exist = false)
    private List<Role> roleBeanList = new ArrayList<>();
    @TableField(exist = false)
    private List<Resource> resourceBeanList = new ArrayList<>();

    public Boolean havePermission(String resource){
        return this.resourceBeanList.stream().filter(resourceBean -> resourceBean.getResourcename().equals(resource)).count()>0;
    }
}
