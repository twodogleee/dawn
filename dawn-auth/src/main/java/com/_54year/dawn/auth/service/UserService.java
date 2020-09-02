package com._54year.dawn.auth.service;

import com._54year.dawn.auth.entity.DawnUser;

public interface UserService {

	/**
	 * 注册用户
	 *
	 * @param dawnUser 用户信息
	 * @return 是否注册成功
	 */
	boolean regist(DawnUser dawnUser);
}
