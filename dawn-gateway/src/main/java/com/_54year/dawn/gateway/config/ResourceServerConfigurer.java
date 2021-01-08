package com._54year.dawn.gateway.config;

import com._54year.dawn.gateway.auth.AuthorizationManager;
import com._54year.dawn.gateway.constant.GatewayConstant;
import com._54year.dawn.gateway.handler.RestAuthenticationEntryPoint;
import com._54year.dawn.gateway.handler.RestfulAccessDeniedHandler;
import com._54year.dawn.jwt.config.JwtProperties;
import com._54year.dawn.redis.util.RedisUtil;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

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
	/**
	 * 未授权处理
	 */
	private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	/**
	 * 未认证处理
	 */
	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	/**
	 * 自定义权限管理
	 */
	private final AuthorizationManager authorizationManager;

	private RedisUtil redisUtil;

	/**
	 * 通过构造方法注入bean
	 */
	ResourceServerConfigurer(JwtProperties jwtProperties, RestfulAccessDeniedHandler restfulAccessDeniedHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint, AuthorizationManager authorizationManager, RedisUtil redisUtil) {
		this.jwtProperties = jwtProperties;
		this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
		this.authorizationManager = authorizationManager;
		this.redisUtil = redisUtil;
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
			.jwtDecoder(new DawnReactiveJwtDecoder(new RsaJsonWebKey(JsonUtil.parseJson(jwtProperties.getPublicKeyStr())).getRsaPublicKey(), redisUtil))
			//权限转换器
			.jwtAuthenticationConverter(jwtAuthenticationConverter());
		http.authorizeExchange()
			.pathMatchers(GatewayConstant.PASS_URL_LIST).permitAll()//白名单配置
			//自定义权限认证
			.anyExchange().access(authorizationManager)
//			.anyExchange().authenticated()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
			.authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
			.and().csrf().disable().cors();
		return http.build();
	}

	/**
	 * JWT授权转换器
	 *
	 * @return 转换器
	 */
	@Bean
	public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		//设置权限标识开头
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix(GatewayConstant.AUTHORITY_PREFIX);
		//设置权限的ClaimName
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(GatewayConstant.AUTHORITY_CLAIM_NAME);
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}


}
