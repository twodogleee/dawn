package com._54year.dawn.gateway.config;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import reactor.core.publisher.Mono;

import java.security.interfaces.RSAPublicKey;


/**
 * 自定义jwt解析
 *
 * @author Andersen
 */
public class DawnReactiveJwtDecoder implements ReactiveJwtDecoder {

	/**
	 * 公钥
	 */
	private final RSAPublicKey publicKey;

	DawnReactiveJwtDecoder(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public Mono<Jwt> decode(String token) throws JwtException {
		NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(publicKey).build();
		Jwt jwt = decoder.decode(token);
		//如果jwt中携带内容中含有 key为ati 说明该jwt是refresh token 禁止验签只能做刷新使用
		if (jwt.containsClaim("ati")) {
			throw new JwtException("Encoded token is a refresh token");
		}
		return Mono.just(jwt);
	}
}
