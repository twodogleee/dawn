package net.sucx.dawn.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置文件 用于获取公私钥
 *
 * @author Andersen
 */
@Configuration
@Data
@RefreshScope
public class JwtProperties {
    /**
     * jwt 公钥
     */
    @Value("${dawn.jwt.publicKeyStr}")
    private String publicKeyStr;
    /**
     * jwt 私钥
     */
    @Value("${dawn.jwt.privateKeyStr}")
    private String privateKeyStr;
	/**
	 * jwt过期时间 单位分钟 默认：60*24*7
	 */
	@Value("${dawn.jwt.accessTokenExpirationTime:10080}")
	private long accessTokenExpirationTime;


}
