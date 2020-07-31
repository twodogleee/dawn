package com._54year.dawn.gateway.config;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import reactor.core.publisher.Mono;

import java.security.interfaces.RSAPublicKey;


public class DawnReactiveJwtDecoder implements ReactiveJwtDecoder {

	private RSAPublicKey publicKey;

	DawnReactiveJwtDecoder(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public Mono<Jwt> decode(String token) throws JwtException {
		NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(publicKey).build();
		Jwt jwt = decoder.decode(token);
		if(jwt.containsClaim("ati")){throw new JwtException("Encoded token is a refresh token");}
		return Mono.just(jwt);
	}
}
