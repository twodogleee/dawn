package com._54year.dawn.admin.controller;

import com._54year.dawn.admin.entity.DawnUserRole;
import com._54year.dawn.admin.feignclient.AuthFeignClient;
import com._54year.dawn.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/feignTest")
public class FeignTest {
	@Autowired
	AuthFeignClient authFeignClient;

	@Autowired
	RedisUtil redisUtil;

	@GetMapping("/test1")
	public Object test1() {
		return authFeignClient.test5();
	}

	@GetMapping("/test2")
	public Object test2(@RequestHeader("Authorization") String authorization) {
		return authFeignClient.test2(authorization);
	}

	@GetMapping("/test3")
	public Object test3() {
//		Map<String, Object> map = new HashMap<>();
//		map.put("aa","aaa");
//		map.put("33",1000000020999901000L);
//		redisUtil.hmset("test2",map);
//		Map<Object, Object> map1 = redisUtil.hmget("test2");
//		System.out.println(map1);
//		redisUtil.set("test","草泥马");
		DawnUserRole userRole = new DawnUserRole();
		userRole.setId("123333");
		userRole.setUserId("aaaacccaa草泥马");
		redisUtil.set("test3",userRole,30);
//		redisUtil.hset("test2","aa","测试测试测试");
		return redisUtil.get("test3");
	}


	@GetMapping("/test4")

//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Object test5(){
		return "Hello world";
	}
}
