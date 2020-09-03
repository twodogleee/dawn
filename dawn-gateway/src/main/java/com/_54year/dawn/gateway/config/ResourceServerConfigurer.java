package com._54year.dawn.gateway.config;

import com._54year.dawn.gateway.auth.AuthorizationManager;
import com._54year.dawn.gateway.constant.GatewayConstant;
import com._54year.dawn.gateway.handler.RestAuthenticationEntryPoint;
import com._54year.dawn.gateway.handler.RestfulAccessDeniedHandler;
import com._54year.dawn.jwt.config.JwtProperties;
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

	private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final AuthorizationManager authorizationManager;

	ResourceServerConfigurer(JwtProperties jwtProperties, RestfulAccessDeniedHandler restfulAccessDeniedHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint, AuthorizationManager authorizationManager) {
		this.jwtProperties = jwtProperties;
		this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
		this.authorizationManager = authorizationManager;
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
			.jwtDecoder(new DawnReactiveJwtDecoder(new RsaJsonWebKey(JsonUtil.parseJson(jwtProperties.getPublicKeyStr())).getRsaPublicKey()))
			.jwtAuthenticationConverter(jwtAuthenticationConverter());
		http.authorizeExchange()
			.pathMatchers(GatewayConstant.PASS_URL_LIST).permitAll()//白名单配置
			.anyExchange().access(authorizationManager)
//			.anyExchange().authenticated()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
			.authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
			.and().csrf().disable().cors();
		return http.build();
	}

	@Bean
	public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix(GatewayConstant.AUTHORITY_PREFIX);
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(GatewayConstant.AUTHORITY_CLAIM_NAME);
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}


}
