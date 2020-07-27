package com._54year.dawn.jwt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置文件 用于获取公私钥 从外部注入
 *
 * @author Andersen
 */
@ConfigurationProperties(prefix = "dawn.jwt")
@Data
@RefreshScope//动态刷新
public class JwtProperties {
	/**
	 * jwt 公钥
	 */
	private String publicKeyStr;
	/**
	 * jwt 私钥
	 */
	private String privateKeyStr;
	/**
	 * jwt过期时间 单位分钟 默认：60*24*7
	 */
	private long accessTokenExpirationTime;


}
