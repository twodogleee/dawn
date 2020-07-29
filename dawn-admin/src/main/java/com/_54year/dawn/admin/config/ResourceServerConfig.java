//package com._54year.dawn.admin.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//
//@Configuration
//@EnableResourceServer
//@Order(5)
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//	@Override
//	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		super.configure(resources);
//	}
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http
////			.authorizeRequests().antMatchers("/test/test2").permitAll().and()  //不需要授权认证
//			.csrf().disable()
//			.exceptionHandling()
//			.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//			.and()
//			.authorizeRequests()
//			.anyRequest().authenticated()
//			.and()
//			.httpBasic();
//	}
//}
//
//
//
