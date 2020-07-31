package com._54year.dawn.gateway.config;

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

	ResourceServerConfigurer(JwtProperties jwtProperties, RestfulAccessDeniedHandler restfulAccessDeniedHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
		this.jwtProperties = jwtProperties;
		this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
	}

	//	private final AuthorizationManager authorizationManager;
	private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws JoseException {
		http.oauth2ResourceServer().jwt()
			.jwtDecoder(new DawnReactiveJwtDecoder(new RsaJsonWebKey(JsonUtil.parseJson(jwtProperties.getPublicKeyStr())).getRsaPublicKey()))
			.jwtAuthenticationConverter(jwtAuthenticationConverter());
		http.authorizeExchange()
			.pathMatchers("/dawn-auth/oauth/**").permitAll()//白名单配置
			.anyExchange().authenticated()
//			.anyExchange().access(authorizationManager)//鉴权管理器配置
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

//		jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
//		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}


}
