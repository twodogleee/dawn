package com._54year.dawn.excel.controller;

import com._54year.dawn.common.annotation.DawnResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@DawnResult
	@PostMapping("test/test1")
	public Object test1() {
		return "测试1123";
	}
}
