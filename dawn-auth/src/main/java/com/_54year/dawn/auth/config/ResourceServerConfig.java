package com._54year.dawn.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源服务器配置类
 *
 * @author Andersen
 */
@Configuration
@EnableResourceServer
@Order(5)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")
			.and()
			.authorizeRequests()
			.antMatchers("/oauth/**").permitAll()
			.and()
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.httpBasic();

	}

}
