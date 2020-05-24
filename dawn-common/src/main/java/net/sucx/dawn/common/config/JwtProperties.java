package net.sucx.dawn.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置文件 用于获取公私钥
 *
 * @author Andersen
 */
@Configuration
@Data
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

}
