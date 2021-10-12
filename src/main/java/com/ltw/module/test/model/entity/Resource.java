package com.ltw.module.test.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ltw.common.model.domain.BaseEntity;
import lombok.Data;

@TableName(value ="resource")
@Data
public class Resource extends BaseEntity {
    private String resourcename;
    private String resourcetype;
}
