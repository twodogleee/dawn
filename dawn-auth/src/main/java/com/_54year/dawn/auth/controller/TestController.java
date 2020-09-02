package com._54year.dawn.auth.controller;

import com._54year.dawn.auth.dao.mapper.UserMapper;
import com.alibaba.fastjson.JSONObject;
import com._54year.dawn.common.annotation.DawnResult;
import com._54year.dawn.common.annotation.HasRole;

import com._54year.dawn.jwt.exception.DawnJwtServiceException;
import com._54year.dawn.jwt.service.JwkUtil;
import com._54year.dawn.jwt.service.JwtService;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	UserMapper userMapper;

	@Autowired
	JwtService jwtService;
	@Autowired
	DataSource dataSource;

	@Autowired
	IdentifierGenerator identifierGenerator;

	@GetMapping("/login")
	public Map<String, Object> test1(@PathParam("loginName") String loginName, HttpServletResponse httpServletResponse) throws DawnJwtServiceException {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("login_name", loginName);
		userInfo.put("user_name", "李二狗");
		userInfo.put("role_name", "admin,user");
		String token = jwtService.createToken(userInfo);
		Map<String, Object> result = new HashMap<>();
		result.put("Authorization", "Bearer " + token);
		result.put("msg", "登录成功");
		Cookie cookie = new Cookie("Authorization", token);
		cookie.setPath("/");
		httpServletResponse.addCookie(cookie);
		return result;
	}


	@PostMapping("/test2")
	@HasRole(roleName = "admin")
	public Object test2(@RequestBody JSONObject param) throws DawnJwtServiceException {
		JwtClaims claims = jwtService.parseToken(JwkUtil.getTokenStr(param.getString("Authorization")));
		System.out.println(claims.getRawJson());
//        return "验证成功user_id=" + claims.getClaimValue("user_id");
		return "验证成功附加内容为" + claims.getRawJson();
	}


	@DawnResult
	@GetMapping("/test4")
	public Object test4() {
		return true;
	}

	@DawnResult
	@GetMapping("/test5")
	public Object test5() {
		List<String> a = new ArrayList<>();
		a.add("aaa");
		return a;
	}

	@DawnResult
	@GetMapping("/test6")
	public Object test6() {
		Map<String, Object> a = new HashMap<>();
		a.put("aaa", 1234);
		return a;
	}

	@DawnResult
	@GetMapping("/test7")
	public Object test7() {
		Map<String, Object> b = new HashMap<>();
		b.put("aaa", 1234);
		Object[] a = {"草泥马", 123, "aaa", b};
		return a;
	}

	@DawnResult
	@GetMapping("/test8")
	public Object test8() {
		return new TestInfo();
	}

	@DawnResult
	@GetMapping("/test9")
	public Object test9() {
		return false;
	}

	@DawnResult
	@GetMapping("/test10")
	public Object test10() {
		return "李二狗";
	}

	@DawnResult
	@GetMapping("/getUser")
	public Object getUser() {
//		List<DawnUser> users = userMapper.selectList(null);
		return userMapper.selectUserList();
	}

	@DawnResult
	@GetMapping("/getId")
	public Object getId() {
		return identifierGenerator.nextId(TestController.class);
	}
}
