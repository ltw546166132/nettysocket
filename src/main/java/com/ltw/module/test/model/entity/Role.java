package com.ltw.module.test.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ltw.common.model.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@TableName(value = "role")
@Data
public class Role extends BaseEntity {
    private Long id;
    private String rolename;

}
