package com._54year.dawn.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当前登录用户信息
 *
 * @author Andersen
 */
@Data
public class CurrentUser implements Serializable {
	private static final long serialVersionUID = 1L;

	public CurrentUser() {
	}

	public CurrentUser(String userId, String nickName, String username, List<String> roleList) {
		this.userId = userId;
		this.nickName = nickName;
		this.username = username;
		this.roleList = roleList;
	}

	/**
	 * userID
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 用户名/登录名
	 */
	private String username;
	/**
	 * 角色list
	 */
	private List<String> roleList;


}
