package com.ltw.module.rnsp171.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.ltw.common.model.domain.BaseEntity;
import lombok.Data;

/**
 * 企业端用户表
 * @TableName t_company_user
 */
@TableName(value ="t_company_user")
@Data
public class CompanyUser extends BaseEntity {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * md5加密盐
     */
    private String salt;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 微信昵称
     */
    private String vxName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 状态
     */
    private String status;


    /**
     * 微信unionId
     */
    private String unionId;

    /**
     * 小程序openId
     */
    private String openId;

}