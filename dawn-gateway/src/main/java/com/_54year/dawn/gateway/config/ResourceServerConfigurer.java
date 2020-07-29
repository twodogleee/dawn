package com._54year.dawn.gateway.config;

import com._54year.dawn.jwt.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;


@Configuration
public class ResourceServerConfigurer {
	/**
	 * Jwt配置
	 */
	private JwtProperties jwtProperties;
	ResourceServerConfigurer(@Autowired JwtProperties jwtProperties){
		this.jwtProperties = jwtProperties;
	}



}
