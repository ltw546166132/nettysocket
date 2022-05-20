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
 * 部门表
 * @TableName t_department
 */
@TableName(value ="t_department")
@Data
public class Department extends BaseEntity {

    /**
     * 备注
     */
    private String remark;


    /**
     * 
     */
    private String deptName;

    /**
     * 
     */
    private Long parentId;

    /**
     * 可见（1-显示；0-隐藏）
     */
    private Integer visible;

    /**
     * 排序
     */
    private Integer sortIndex;

    /**
     * 平台类型：1：智工建企业端  2：智工建项目端
     */
    private Integer deptPlatform;

    /**
     * 树结构层级
     */
    private Integer level;

    /**
     * 所属平台的id  比如company_id, project_id
     */
    private Long platformId;

    /**
     * 部门编号
     */
    private String deptCode;


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
        Department other = (Department) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getDeptName() == null ? other.getDeptName() == null : this.getDeptName().equals(other.getDeptName()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getVisible() == null ? other.getVisible() == null : this.getVisible().equals(other.getVisible()))
            && (this.getSortIndex() == null ? other.getSortIndex() == null : this.getSortIndex().equals(other.getSortIndex()))
            && (this.getDeptPlatform() == null ? other.getDeptPlatform() == null : this.getDeptPlatform().equals(other.getDeptPlatform()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getPlatformId() == null ? other.getPlatformId() == null : this.getPlatformId().equals(other.getPlatformId()))
            && (this.getDeptCode() == null ? other.getDeptCode() == null : this.getDeptCode().equals(other.getDeptCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getDeptName() == null) ? 0 : getDeptName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getVisible() == null) ? 0 : getVisible().hashCode());
        result = prime * result + ((getSortIndex() == null) ? 0 : getSortIndex().hashCode());
        result = prime * result + ((getDeptPlatform() == null) ? 0 : getDeptPlatform().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getPlatformId() == null) ? 0 : getPlatformId().hashCode());
        result = prime * result + ((getDeptCode() == null) ? 0 : getDeptCode().hashCode());
        return result;
    }

}