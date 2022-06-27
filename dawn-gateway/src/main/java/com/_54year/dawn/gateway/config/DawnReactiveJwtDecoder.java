package com._54year.dawn.gateway.config;

import com._54year.dawn.core.constant.DawnRedisKeyConstant;
import com._54year.dawn.jwt.exception.DawnJwtServiceException;
import com._54year.dawn.redis.util.RedisUtil;
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
	/**
	 * redis 工具
	 */
	private RedisUtil redisUtil;

	DawnReactiveJwtDecoder(RSAPublicKey publicKey, RedisUtil redisUtil) {
		this.publicKey = publicKey;
		this.redisUtil = redisUtil;
	}

	@Override
	public Mono<Jwt> decode(String token) throws JwtException {
		NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(publicKey).build();
		Jwt jwt = decoder.decode(token);
		//如果jwt中携带内容中含有 key为ati 说明该jwt是refresh token 禁止验签只能做刷新使用
		if (jwt.containsClaim("ati")) {
			throw new JwtException("Encoded token is a refresh token");
		}
		//如果jwt中含有user_id 则查询redis中当前client对应的tokenId是否与当前tokenId一致
		if (jwt.containsClaim("user_id")) {
			Object tokenId = redisUtil.get(DawnRedisKeyConstant.DAWN_AUTH_TOKEN
				+ jwt.getClaimAsString("user_id")
				+ DawnRedisKeyConstant.DAWN_REDIS_SEPARATOR_CHAR
				+ jwt.getClaimAsString("client_id"));
			if (tokenId == null || !tokenId.toString().equals(jwt.getId())) {
				throw new JwtException("token已失效");
			}
		}
		return Mono.just(jwt);
	}
}
