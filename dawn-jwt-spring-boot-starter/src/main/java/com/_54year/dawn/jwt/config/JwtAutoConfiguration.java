package com._54year.dawn.jwt.config;

import com._54year.dawn.jwt.service.JwtService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnWebApplication //如果应用是web时才进行生效（配置）
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {
	@Bean
	JwtService jwtService() {
		return new JwtService();
	}
}
