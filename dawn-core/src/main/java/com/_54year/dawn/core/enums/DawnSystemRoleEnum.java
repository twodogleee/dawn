package com._54year.dawn.core.enums;

/**
 * 角色枚举值
 *
 * @author Andersen
 */
public enum DawnSystemRoleEnum {
	ADMIN(1000, "admin"),
	USER(1001, "user");

	/**
	 * 角色code
	 */
	private Integer roleId;
	/**
	 * 角色标识
	 */
	private String roleName;

	DawnSystemRoleEnum(Integer roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Integer roleId() {
		return roleId;
	}

	public String roleName() {
		return roleName;
	}


}
