package com._54year.dawn.gateway.config;

import com._54year.dawn.gateway.constant.GatewayConstant;
import com._54year.dawn.gateway.handler.RestAuthenticationEntryPoint;
import com._54year.dawn.gateway.handler.RestfulAccessDeniedHandler;
import com._54year.dawn.jwt.config.JwtProperties;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 资源安全配置类 统一验签
 *
 * @author Andersen
 */

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfigurer {


	/**
	 * Jwt配置
	 */
	private JwtProperties jwtProperties;

	private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	ResourceServerConfigurer(JwtProperties jwtProperties, RestfulAccessDeniedHandler restfulAccessDeniedHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
		this.jwtProperties = jwtProperties;
		this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
	}

	/**
	 * 安全配置
	 *
	 * @param http http
	 * @return 过滤器
	 * @throws JoseException jwt异常
	 */
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws JoseException {
		http.oauth2ResourceServer().jwt()
			//自定义Jwt解析
			.jwtDecoder(new DawnReactiveJwtDecoder(new RsaJsonWebKey(JsonUtil.parseJson(jwtProperties.getPublicKeyStr())).getRsaPublicKey()));
		http.authorizeExchange()
			.pathMatchers(GatewayConstant.PASS_URL_LIST).permitAll()//白名单配置
			.anyExchange().authenticated()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
			.authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
			.and().csrf().disable().cors();
		return http.build();
	}


}
