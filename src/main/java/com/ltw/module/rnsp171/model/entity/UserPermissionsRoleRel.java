package com.ltw.module.rnsp171.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色权限关联表
 * @TableName user_permissions_role_rel
 */
@TableName(value ="user_permissions_role_rel")
@Data
public class UserPermissionsRoleRel implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 
     */
    private String roleCode;

    /**
     * 
     */
    private String roleName;

    /**
     * 权限id
     */
    private Long permissionsId;

    /**
     * 
     */
    private String permissionsCode;

    /**
     * 
     */
    private String permissionsName;

    /**
     * 
     */
    private Long parentId;

    /**
     * 
     */
    private Integer type;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private String icon;

    /**
     * 
     */
    private Integer isSystem;

    /**
     * 
     */
    private Integer sortIndex;

    /**
     * 
     */
    private String url;

    /**
     * 
     */
    private Integer roleType;

    /**
     * 
     */
    private Long roleRelId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        UserPermissionsRoleRel other = (UserPermissionsRoleRel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getRoleCode() == null ? other.getRoleCode() == null : this.getRoleCode().equals(other.getRoleCode()))
            && (this.getRoleName() == null ? other.getRoleName() == null : this.getRoleName().equals(other.getRoleName()))
            && (this.getPermissionsId() == null ? other.getPermissionsId() == null : this.getPermissionsId().equals(other.getPermissionsId()))
            && (this.getPermissionsCode() == null ? other.getPermissionsCode() == null : this.getPermissionsCode().equals(other.getPermissionsCode()))
            && (this.getPermissionsName() == null ? other.getPermissionsName() == null : this.getPermissionsName().equals(other.getPermissionsName()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
            && (this.getIsSystem() == null ? other.getIsSystem() == null : this.getIsSystem().equals(other.getIsSystem()))
            && (this.getSortIndex() == null ? other.getSortIndex() == null : this.getSortIndex().equals(other.getSortIndex()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getRoleType() == null ? other.getRoleType() == null : this.getRoleType().equals(other.getRoleType()))
            && (this.getRoleRelId() == null ? other.getRoleRelId() == null : this.getRoleRelId().equals(other.getRoleRelId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getRoleCode() == null) ? 0 : getRoleCode().hashCode());
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        result = prime * result + ((getPermissionsId() == null) ? 0 : getPermissionsId().hashCode());
        result = prime * result + ((getPermissionsCode() == null) ? 0 : getPermissionsCode().hashCode());
        result = prime * result + ((getPermissionsName() == null) ? 0 : getPermissionsName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getIsSystem() == null) ? 0 : getIsSystem().hashCode());
        result = prime * result + ((getSortIndex() == null) ? 0 : getSortIndex().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getRoleType() == null) ? 0 : getRoleType().hashCode());
        result = prime * result + ((getRoleRelId() == null) ? 0 : getRoleRelId().hashCode());
        return result;
    }

}