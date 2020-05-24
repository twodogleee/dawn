package net.sucx.dawn.common.jwt;

import net.sucx.dawn.common.config.JwtProperties;
import net.sucx.dawn.common.exception.JwtServiceException;
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
import org.springframework.stereotype.Component;

import java.security.PrivateKey;

/**
 * JWT加密解密
 *
 * @author Andersen
 */
@Component
public class JwtService {
    /**
     * Jwt配置
     */
    @Autowired
    private JwtProperties jwtProperties;
    /**
     * jwt过期时间 分钟
     */
    public static long accessTokenExpirationTime = 60 * 24 * 10;

    /**
     * 创建token jwt
     *
     * @param user_id 附加信息
     * @return token信息
     * @throws JwtServiceException jwt业务异常
     */
    public String createToken(String user_id) throws JwtServiceException {
        String privateKeyStr = jwtProperties.getPrivateKeyStr();
        if (privateKeyStr == null || privateKeyStr.trim().isEmpty()) {
            throw new JwtServiceException("获取RSA私钥失败！");
        }
        try {
            //Payload
            JwtClaims claims = new JwtClaims();
            //设置JWT的唯一ID
            claims.setGeneratedJwtId();
            //发行时间
            claims.setIssuedAtToNow();
            //过期时间
            claims.setExpirationTimeMinutesInTheFuture(accessTokenExpirationTime);
            //在此时间不可用 生效时间
            claims.setNotBeforeMinutesInThePast(1);
            //所有人
            claims.setSubject("Dawn");
            //接收人
//            claims.setAudience("YOUR_AUDIENCE");
            //添加自定义参数,必须是字符串类型
            claims.setClaim("user_id", user_id);
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
    public JwtClaims parseToken(String token) throws JwtServiceException {
        String publicKeyStr = jwtProperties.getPublicKeyStr();
        if (publicKeyStr == null || publicKeyStr.trim().isEmpty()) {
            throw new JwtServiceException("获取RSA公钥失败！");
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
            throw new JwtServiceException("验证Token失败！");
        }
        return null;
    }
}
