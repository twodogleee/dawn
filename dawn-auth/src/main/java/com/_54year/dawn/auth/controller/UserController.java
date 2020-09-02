package com._54year.dawn.auth.controller;

import com._54year.dawn.auth.entity.DawnUser;
import com._54year.dawn.auth.service.UserService;
import com._54year.dawn.common.annotation.DawnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关接口
 *
 * @author Andersen
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * 注册用户
	 *
	 * @param dawnUser 用户信息
	 * @return 是否注册成功
	 */
	@PostMapping("/regist")
	@DawnResult
	public Object regist(@RequestBody DawnUser dawnUser) {
		return userService.regist(dawnUser);
	}


}
