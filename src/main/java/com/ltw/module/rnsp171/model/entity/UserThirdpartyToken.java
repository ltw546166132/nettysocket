package com.ltw.module.rnsp171.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ltw.common.model.domain.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName t_user_thirdparty_token
 */
@TableName(value ="t_user_thirdparty_token")
@Data
public class UserThirdpartyToken extends BaseEntity {

    /**
     * user表外键
     */
    private Long userId;

    /**
     * 微信unionId
     */
    private String unionId;

    /**
     * 小程序openId
     */
    private String openId;

    /**
     * 1: 微信公众号
     */
    private Integer type;

    /**
     * 
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}