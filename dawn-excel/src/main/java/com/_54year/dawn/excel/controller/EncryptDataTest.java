package com._54year.dawn.excel.controller;

import com._54year.dawn.common.annotation.DawnResult;
import com._54year.dawn.common.annotation.EncryptDataMethod;
import com._54year.dawn.excel.entity.ExcelDemo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encryptDataTest")
public class EncryptDataTest {

	@DawnResult
	@RequestMapping("/encryptData")
	@EncryptDataMethod("def")
	public Object encryptData(@RequestBody ExcelDemo req) {
		return req;
	}
}
