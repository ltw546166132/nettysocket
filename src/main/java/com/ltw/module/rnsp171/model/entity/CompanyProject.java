package com.ltw.module.rnsp171.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ltw.common.model.domain.BaseEntity;
import lombok.Data;

/**
 * 项目表
 * @TableName t_company_project
 */
@TableName(value ="t_company_project")
@Data
public class CompanyProject extends BaseEntity {

    /**
     * 企业id
     */
    private Integer corpId;

    /**
     * appId
     */
    private String appId;

    /**
     * 密钥
     */
    private String appSecret;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目登录code
     */
    private String loginCode;

    /**
     * 项目登录密码
     */
    private String loginPassword;

    /**
     * 同步状态
     */
    private String syncStatus;

    /**
     * 项目地址
     */
    private String address;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 项目类型
     */
    private String category;

    /**
     * 项目状态
     */
    private String status;

    /**
     * 工人平台项目状态
     */
    private String prjStatus;

    /**
     * 是否纳入监管，0否1是
     */
    private Integer supervision;

    /**
     * 施工许可证
     */
    private String builderLicenses;

    /**
     * 项目工期
     */
    private Integer schedule;

    /**
     * 行政主管部门
     */
    private String managerDept;

    /**
     * 中标合同价格
     */
    private BigDecimal winContractPrice;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date completeDate;

    /**
     * 考勤地址
     */
    private String attendanceAddress;

    /**
     * 地址纬度
     */
    private BigDecimal lat;

    /**
     * 地址经度
     */
    private BigDecimal lng;

    /**
     * 考勤范围
     */
    private BigDecimal attendanceArea;

    /**
     * 考勤名称
     */
    private String attendanceLabel;

    /**
     * 下属参建单位个数
     */
    private Integer contractorNum;

    /**
     * 读卡器类型
     */
    private String cardReaderType;

    /**
     * 同步平台类型
     */
    private String platformType;

    /**
     * 权限开关
     */
    private Integer permissionSwitch;

    /**
     * 银行卡号
     */
    private String payBankCardNo;

    /**
     * 开户行名称
     */
    private String payBankName;

    /**
     * 银行名称(枚举)
     */
    private String payBankCode;


    /**
     * 项目地址经度
     */
    private String addressX;

    /**
     * 项目地址纬度
     */
    private String addressY;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 建筑面积
     */
    private BigDecimal constructionArea;

    /**
     * 建筑单位
     */
    private String constructionUnit;

    /**
     * 监督机构
     */
    private String supervisoryDept;

    /**
     * 项目介绍
     */
    private String remark;

    /**
     * 全景图
     */
    private String panorama;

    /**
     * 俯视图
     */
    private String planform;

    /**
     * 监测图
     */
    private String monitoring;

    /**
     * 项目分类 0:其他 1:房屋建筑工程 2:市政工程 3:公路工程
     */
    private String classify;

    /**
     * 项目权限状态 1:正常  2:项目禁用
     */
    private String projectPermission;

}