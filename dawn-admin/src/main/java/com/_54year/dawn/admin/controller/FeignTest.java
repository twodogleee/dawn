package com._54year.dawn.admin.controller;

import com._54year.dawn.admin.feignclient.AuthFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feignTest")
public class FeignTest {
	@Autowired
	AuthFeignClient authFeignClient;

	@GetMapping("/test1")
	public Object test1() {
		return authFeignClient.test1();
	}

	@GetMapping("/test2")
	public Object test2(@RequestHeader("Authorization") String authorization) {
		return authFeignClient.test2(authorization);
	}

	@GetMapping("/test3")
	public Object test3() {
		return "Hello world";
	}


	@GetMapping("/test4")

//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Object test5(){
		return "Hello world";
	}
}
