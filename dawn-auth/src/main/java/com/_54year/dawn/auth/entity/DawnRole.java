package com._54year.dawn.auth.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * dawn-角色实体类
 *
 * @author Andersen
 */
public class DawnRole implements GrantedAuthority {
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 角色标识
	 */
	private String roleName;

	public DawnRole() {
	}

	public DawnRole(Integer roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

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

	/**
	 * 获取权限
	 *
	 * @return 以角色标识作为权限标识
	 */
	@Override
	public String getAuthority() {
		return roleName;
	}
}
