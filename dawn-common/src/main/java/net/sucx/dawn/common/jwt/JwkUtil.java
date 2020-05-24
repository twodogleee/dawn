package net.sucx.dawn.common.jwt;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.lang.JoseException;

import java.util.UUID;

/**
 * Json Web Key工具
 *
 * @author Andersen
 */
public class JwkUtil {
    /**
     * token前缀
     */
    public static String BEARER = "bearer";
    /**
     * token长度
     */
    public static Integer AUTH_LENGTH = 7;

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

    /**
     * 获取Token字符串
     *
     * @param authorization 签证信息
     * @return token
     */
    public static String getTokenStr(String authorization) {
        if (authorization != null && authorization.length() > AUTH_LENGTH) {
            String headStr = authorization.substring(0, 6).toLowerCase();
            if (headStr.compareTo(BEARER) == 0) {
                authorization = authorization.substring(7);
            }
            return authorization;
        } else {
            return null;
        }
    }


}
