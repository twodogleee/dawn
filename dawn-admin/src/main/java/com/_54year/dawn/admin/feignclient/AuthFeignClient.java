package com._54year.dawn.admin.feignclient;

import com._54year.dawn.common.config.DawnFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "dawn-auth", configuration = DawnFeignConfiguration.class)
public interface AuthFeignClient {
	@RequestMapping(value = "/test/test1", method = RequestMethod.GET)
	String test1();

	@RequestMapping(value = "/test/test2", method = RequestMethod.POST)
	String test2(@RequestHeader("Authorization") String authorization);

	@GetMapping("/test/test3")
	String test3();

	@GetMapping("/test/test4")
	String test4();
	@GetMapping("/test/test5")
	String test5();
	@GetMapping("/test/test6")
	String test6();

}
