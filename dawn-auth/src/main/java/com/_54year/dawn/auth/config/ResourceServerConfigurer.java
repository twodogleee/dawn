package com._54year.dawn.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * 服务资源安全配置
 *
 * @author Andersen
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and()
			.authorizeRequests()
			.antMatchers("/oauth/**","test/**","/user/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic();
	}
}
