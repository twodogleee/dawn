package com._54year.dawn.auth.controller;

import com._54year.dawn.common.annotation.DawnResult;
import com._54year.dawn.jwt.config.JwtProperties;
import com._54year.dawn.jwt.exception.DawnJwtServiceException;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;

@RestController
@RequestMapping("/client")
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
