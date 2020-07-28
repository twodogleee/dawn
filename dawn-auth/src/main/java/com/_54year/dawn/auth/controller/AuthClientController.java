package com._54year.dawn.auth.controller;

import com._54year.dawn.common.annotation.DawnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class AuthClientController {
	/**
	 * 客户端信息处理
	 */
	@Autowired
	private JdbcClientDetailsService clientDetailsService;

	/**
	 * @param baseClientDetails
	 * @return
	 */
	@PostMapping("/addClientDetails")
	@DawnResult
	public Object addClient(@RequestBody BaseClientDetails baseClientDetails) {
		clientDetailsService.addClientDetails(baseClientDetails);
		return true;
	}


}
