package com._54year.dawn.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * dawn-用户与角色对应表
 * </p>
 *
 * @author Andersen
 * @since 2020-09-02
 */
public class DawnUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

	public DawnUserRole(){}

	public DawnUserRole(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "DawnUserRole{" +
        "id=" + id +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
