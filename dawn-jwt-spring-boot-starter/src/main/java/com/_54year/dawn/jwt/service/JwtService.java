package com._54year.dawn.jwt.service;


import com._54year.dawn.jwt.config.JwtProperties;
import com._54year.dawn.jwt.exception.DawnJwtServiceException;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.PrivateKey;
import java.util.Map;

/**
 * JWT加密解密
 *
 * @author Andersen
 */
public class JwtService {
	/**
	 * Jwt配置
	 */
	@Autowired
	private JwtProperties jwtProperties;


	/**
	 * 创建token jwt
	 *
	 * @param extras 附加信息
	 * @return token信息
	 * @throws DawnJwtServiceException jwt业务异常
	 */
	public String createToken(Map<String, Object> extras) throws DawnJwtServiceException {
		String privateKeyStr = jwtProperties.getPrivateKeyStr();
		if (privateKeyStr == null || privateKeyStr.trim().isEmpty()) {
			throw new DawnJwtServiceException("获取RSA私钥失败！");
		}
		try {
			//Payload
			JwtClaims claims = new JwtClaims();
			//设置JWT的唯一ID
			claims.setGeneratedJwtId();
			//发行时间
			claims.setIssuedAtToNow();
			//过期时间
			claims.setExpirationTimeMinutesInTheFuture(jwtProperties.getAccessTokenExpirationTime());
			//在此时间不可用 生效时间
			claims.setNotBeforeMinutesInThePast(1);
			//所有人
			claims.setSubject("Dawn");
			//接收人
//            claims.setAudience("YOUR_AUDIENCE");
			//添加自定义参数
			extras.forEach(claims::setClaim);
			//jws
			JsonWebSignature jws = new JsonWebSignature();
			//签名算法RS256
			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
//            jws.setKeyIdHeaderValue(keyId);
			jws.setHeader("typ", "JWT");
			jws.setPayload(claims.toJson());
			//将jwk转Key对象
			PrivateKey privateKey = new RsaJsonWebKey(JsonUtil.parseJson(privateKeyStr)).getPrivateKey();
			jws.setKey(privateKey);
			//get token
			return jws.getCompactSerialization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * jws校验token
	 *
	 * @param token token信息
	 * @return 返回携带信息
	 */
	public JwtClaims parseToken(String token) throws DawnJwtServiceException {
		String publicKeyStr = jwtProperties.getPublicKeyStr();
		if (publicKeyStr == null || publicKeyStr.trim().isEmpty()) {
			throw new DawnJwtServiceException("获取RSA公钥失败！");
		}
		try {
			JwtConsumer consumer = new JwtConsumerBuilder()
				.setRequireExpirationTime()
				.setAllowedClockSkewInSeconds(30)
				.setVerificationKey(new RsaJsonWebKey(JsonUtil.parseJson(publicKeyStr)).getPublicKey())
				.build();

			//获取携带内容
			JwtClaims claims = consumer.processToClaims(token);
			if (claims != null) {
//                System.out.println("认证通过！");
//                System.out.println(claims.toJson());
				return claims;
			}
		} catch (InvalidJwtException | JoseException e) {
			throw new DawnJwtServiceException("验证Token失败！");
		}
		return null;
	}
}
