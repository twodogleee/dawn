package com._54year.dawn.admin.entity;

import java.io.Serializable;

/**
 * <p>
 * dawn-角色表
 * </p>
 *
 * @author Andersen
 * @since 2020-09-04
 */
public class DawnRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "DawnRole{" +
        "roleId=" + roleId +
        ", roleName=" + roleName +
        "}";
    }
}
