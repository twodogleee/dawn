package com._54year.dawn.admin.entity;

import java.io.Serializable;

/**
 * <p>
 * dawn-角色与资源对应表
 * </p>
 *
 * @author Andersen
 * @since 2020-09-07
 */
public class DawnRoleResources implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 资源ID
     */
    private String resourcesId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }

    @Override
    public String toString() {
        return "DawnRoleResources{" +
        "id=" + id +
        ", roleId=" + roleId +
        ", resourcesId=" + resourcesId +
        "}";
    }
}
