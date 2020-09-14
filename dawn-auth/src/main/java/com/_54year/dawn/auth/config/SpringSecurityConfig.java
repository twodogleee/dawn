package com._54year.dawn.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置类
 *
 * @author Andersen
 */

@Configuration
@EnableWebSecurity
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("dawnUserDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	/**
	 * password加密
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 身份认证类 配置身份认证模式
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setHideUserNotFoundExceptions(false);
		return authenticationProvider;
	}

	/**
	 * Http安全配置
	 *
	 * @param http http安全配置
	 * @throws Exception 异常
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		//安全配置请求匹配 只有在requestMatchers()中匹配的路径才会受该处配置影响 其他路径移交到下一配置 则资源安全配置管理
		http.requestMatchers()
			.antMatchers("/oauth/**", "/authentication/**", "/login")
			.and()
			//表单登录配置
			.formLogin()
			//登录跳转路径
			.loginPage("/authentication/require")
			//表单登录处理接口
			.loginProcessingUrl("/authentication/form")
			.and()
			//访问验证
			.authorizeRequests()
//			.antMatchers("/oauth/**").authenticated().anyRequest().permitAll();
			//以下路径开放访问
			.antMatchers(
				"/login",
				"/oauth/confirm_access",
				"/authentication/require",
				"/authentication/form"
			).permitAll()
			//其他路径全部需要认证 如果未通过认证则会拦截到登录页
			.anyRequest().authenticated();
	}

	/**
	 * 创建AuthenticationManager 构建身份验证
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	/**
	 * Authentication管理者 定义authenticationManager 不定义没有password grant_type
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
