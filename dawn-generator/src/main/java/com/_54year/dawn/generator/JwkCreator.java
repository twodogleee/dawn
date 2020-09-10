package com._54year.dawn.generator;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.lang.JoseException;

import java.util.UUID;

/**
 * 认证服务器使用到的jwk公私钥创建器
 *
 * @author Andersen
 */
public class JwkCreator {

	public static void main(String[] args) {
		createKeyPair();
	}


	/**
	 * 创建jwk  公钥 ，秘钥
	 */
	public static void createKeyPair() {
		String keyId = UUID.randomUUID().toString().replaceAll("-", "");
		RsaJsonWebKey jwk = null;
		try {
			//设置JWK 加密位数
			jwk = RsaJwkGenerator.generateJwk(2048);
			jwk.setKeyId(keyId);
			//采用的签名算法 RS256
			jwk.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);
			String publicKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.PUBLIC_ONLY);
			String privateKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
			System.out.println("keyId=" + keyId);
			System.out.println();
			System.out.println("公钥 publicKeyStr=" + publicKey);
			System.out.println();
			System.out.println("私钥 privateKeyStr=" + privateKey);
		} catch (JoseException e) {
			e.printStackTrace();
		}
	}

}
