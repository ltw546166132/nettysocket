package com.ltw.module.test.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ltw.common.model.domain.BaseEntity;
import lombok.Data;

@TableName(value ="permission")
@Data
public class Permission extends BaseEntity {
    private String code;
    private String permissionName;
    private String permissionUrl;
}
