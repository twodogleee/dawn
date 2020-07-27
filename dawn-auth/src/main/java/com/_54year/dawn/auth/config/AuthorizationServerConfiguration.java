package com._54year.dawn.auth.config;

import com._54year.dawn.jwt.config.JwtProperties;
import com._54year.dawn.jwt.exception.DawnJwtServiceException;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务器配置
 *
 * @author Andersen
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	@Autowired
	@Qualifier("authenticationManagerBean")//指定注入bean名称
	AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private TokenStore tokenStore;

	/**
	 * Jwt配置
	 */
	private JwtProperties jwtProperties;

//	static final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);

//	AuthorizationServerConfiguration(){}
	AuthorizationServerConfiguration(@Autowired JwtProperties jwtProperties){
		this.jwtProperties = jwtProperties;
	}

	@Bean
	public TokenStore tokenStore() throws JoseException {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() throws JoseException {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		String privateKeyStr = jwtProperties.getPrivateKeyStr();
		String publicKeyStr = jwtProperties.getPublicKeyStr();
		if (StringUtils.isAllBlank(privateKeyStr,publicKeyStr)) {
			throw new DawnJwtServiceException("获取RSA秘钥失败！");
		}
		//设置公私秘钥
		KeyPair keyPair = new KeyPair(
			new RsaJsonWebKey(JsonUtil.parseJson(publicKeyStr)).getPublicKey(),
			new RsaJsonWebKey(JsonUtil.parseJson(privateKeyStr)).getPrivateKey()
		);
		converter.setKeyPair(keyPair);
		return converter;
	}



	/*
	 * 配置客户端详情信息(内存或JDBC来实现)
	 *
	 * */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//初始化 Client 数据到 DB
		// clients.jdbc(dataSource)
		clients.inMemory()
			.withClient("client_1")
			.authorizedGrantTypes("client_credentials")
			.scopes("all","read", "write")
			.authorities("client_credentials")
			.accessTokenValiditySeconds(7200)
			.secret(new BCryptPasswordEncoder().encode("123456"))

			.and().withClient("client_2")
			.authorizedGrantTypes("password", "refresh_token")
			.scopes("all","read", "write")
			.accessTokenValiditySeconds(7200)
			.refreshTokenValiditySeconds(10000)
			.authorities("password")
			.secret(new BCryptPasswordEncoder().encode("123456"))

			.and().withClient("client_3").authorities("authorization_code","refresh_token")
			.secret(new BCryptPasswordEncoder().encode("123456"))
			.authorizedGrantTypes("authorization_code")
			.scopes("all","read", "write")
			.accessTokenValiditySeconds(7200)
			.refreshTokenValiditySeconds(10000)
			.redirectUris("http://localhost:8080/callback","http://localhost:8080/signin")

			.and().withClient("client_test")
			.secret(new BCryptPasswordEncoder().encode("123456"))
			.authorizedGrantTypes("all flow")
			.authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token","password", "implicit")
			.redirectUris("http://localhost:8080/callback","http://localhost:8080/signin","http://localhost:8082/login")
			.scopes("all","read", "write")
			.accessTokenValiditySeconds(7200)
			.refreshTokenValiditySeconds(10000);

		//https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql
		// clients.withClientDetails(new JdbcClientDetailsService(dataSource));
	}

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}



	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).tokenEnhancer(jwtAccessTokenConverter()).authenticationManager(authenticationManager);

		// 配置tokenServices参数
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(endpoints.getTokenStore());
		tokenServices.setSupportRefreshToken(false);
		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
		tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)); // 30天
		endpoints.tokenServices(tokenServices);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 允许表单认证
		security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}


}
