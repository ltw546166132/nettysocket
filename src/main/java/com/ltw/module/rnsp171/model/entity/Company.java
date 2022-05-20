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
 * 企业表
 * @TableName t_company
 */
@TableName(value ="t_company")
@Data
public class Company extends BaseEntity {

    /**
     * 企业名称
     */
    private String corpName;

    /**
     * 企业logo
     */
    private String logo;

    /**
     * 统一社会信用代码
     */
    private String corpCode;

    /**
     * 企业法人
     */
    private String legalMan;

    /**
     * 认证状态
     */
    private String authStatus;

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
     * 注册地址
     */
    private String address;

    /**
     * 法人联系方式
     */
    private String legalManTel;

    /**
     * 公司类型
     */
    private String corpType;

    /**
     * 注册日期
     */
    private Date registerDate;

    /**
     * 办公电话
     */
    private String officePhone;

    /**
     * 注册资金
     */
    private BigDecimal regCapital;

    /**
     * 保证金
     */
    private BigDecimal deposit;

    /**
     * 联系人
     */
    private String linkMan;

    /**
     * 联系电话
     */
    private String linkTel;

    /**
     * 企业联系邮箱
     */
    private String email;

    /**
     * 劳资专管员
     */
    private String laborManager;

    /**
     * 劳资专管员联系方式
     */
    private String laborManagerTel;

    /**
     * 企业网址
     */
    private String website;

    /**
     * 备注
     */
    private String remark;


    /**
     * 是否运营后台添加临时企业
     */
    private Integer tempState;


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Company other = (Company) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCorpName() == null ? other.getCorpName() == null : this.getCorpName().equals(other.getCorpName()))
            && (this.getLogo() == null ? other.getLogo() == null : this.getLogo().equals(other.getLogo()))
            && (this.getCorpCode() == null ? other.getCorpCode() == null : this.getCorpCode().equals(other.getCorpCode()))
            && (this.getLegalMan() == null ? other.getLegalMan() == null : this.getLegalMan().equals(other.getLegalMan()))
            && (this.getAuthStatus() == null ? other.getAuthStatus() == null : this.getAuthStatus().equals(other.getAuthStatus()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getRegion() == null ? other.getRegion() == null : this.getRegion().equals(other.getRegion()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getLegalManTel() == null ? other.getLegalManTel() == null : this.getLegalManTel().equals(other.getLegalManTel()))
            && (this.getCorpType() == null ? other.getCorpType() == null : this.getCorpType().equals(other.getCorpType()))
            && (this.getRegisterDate() == null ? other.getRegisterDate() == null : this.getRegisterDate().equals(other.getRegisterDate()))
            && (this.getOfficePhone() == null ? other.getOfficePhone() == null : this.getOfficePhone().equals(other.getOfficePhone()))
            && (this.getRegCapital() == null ? other.getRegCapital() == null : this.getRegCapital().equals(other.getRegCapital()))
            && (this.getDeposit() == null ? other.getDeposit() == null : this.getDeposit().equals(other.getDeposit()))
            && (this.getLinkMan() == null ? other.getLinkMan() == null : this.getLinkMan().equals(other.getLinkMan()))
            && (this.getLinkTel() == null ? other.getLinkTel() == null : this.getLinkTel().equals(other.getLinkTel()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getLaborManager() == null ? other.getLaborManager() == null : this.getLaborManager().equals(other.getLaborManager()))
            && (this.getLaborManagerTel() == null ? other.getLaborManagerTel() == null : this.getLaborManagerTel().equals(other.getLaborManagerTel()))
            && (this.getWebsite() == null ? other.getWebsite() == null : this.getWebsite().equals(other.getWebsite()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getTempState() == null ? other.getTempState() == null : this.getTempState().equals(other.getTempState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCorpName() == null) ? 0 : getCorpName().hashCode());
        result = prime * result + ((getLogo() == null) ? 0 : getLogo().hashCode());
        result = prime * result + ((getCorpCode() == null) ? 0 : getCorpCode().hashCode());
        result = prime * result + ((getLegalMan() == null) ? 0 : getLegalMan().hashCode());
        result = prime * result + ((getAuthStatus() == null) ? 0 : getAuthStatus().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getRegion() == null) ? 0 : getRegion().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getLegalManTel() == null) ? 0 : getLegalManTel().hashCode());
        result = prime * result + ((getCorpType() == null) ? 0 : getCorpType().hashCode());
        result = prime * result + ((getRegisterDate() == null) ? 0 : getRegisterDate().hashCode());
        result = prime * result + ((getOfficePhone() == null) ? 0 : getOfficePhone().hashCode());
        result = prime * result + ((getRegCapital() == null) ? 0 : getRegCapital().hashCode());
        result = prime * result + ((getDeposit() == null) ? 0 : getDeposit().hashCode());
        result = prime * result + ((getLinkMan() == null) ? 0 : getLinkMan().hashCode());
        result = prime * result + ((getLinkTel() == null) ? 0 : getLinkTel().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getLaborManager() == null) ? 0 : getLaborManager().hashCode());
        result = prime * result + ((getLaborManagerTel() == null) ? 0 : getLaborManagerTel().hashCode());
        result = prime * result + ((getWebsite() == null) ? 0 : getWebsite().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getTempState() == null) ? 0 : getTempState().hashCode());
        return result;
    }

}