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
import org.springframework.security.core.userdetails.UserDetailsService;
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
 * 认证服务器配置类
 * 通过继承AuthorizationServerConfigurerAdapter并且覆写其中的三个configure方法来进行配置
 *
 * @author Andersen
 */
@Configuration
@EnableAuthorizationServer //该注解用于开启OAuth2授权服务机制
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	@Autowired
	@Qualifier("authenticationManagerBean")//指定注入bean名称
		AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	@Qualifier("dawnUserDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	/**
	 * Jwt配置
	 */
	private JwtProperties jwtProperties;

	/**
	 * 重新构造方法 注入jwt公私钥
	 *
	 * @param jwtProperties jwt配置信息
	 */
	AuthorizationServerConfiguration(@Autowired JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	/**
	 * ClientDetailsServiceConfigurer
	 * 用于定义客户详细信息服务的配置器。
	 * 客户端详情信息进行初始化，能够把客户端详情信息写在内存中或者是通过数据库来存储调取详情信息
	 *
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//官方提供的SQL地址
		//https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql
		//从DB中读取Client信息
//		clients.jdbc(dataSource);
//		clients.inMemory() //从内存中读取
//			.withClient("client_1")
//			.authorizedGrantTypes("client_credentials")
//			.scopes("all", "read", "write")
//			.authorities("client_credentials")
//			.accessTokenValiditySeconds(7200)
//			.secret(new BCryptPasswordEncoder().encode("123456"))
//
//			.and().withClient("client_test")
//			.secret(new BCryptPasswordEncoder().encode("123456"))
//			.authorizedGrantTypes("all flow")
//			.authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token", "password", "implicit")
//			.redirectUris("http://localhost:8080/callback", "http://localhost:8080/signin", "http://localhost:8083/login")
//			.scopes("all", "read", "write")
//			.accessTokenValiditySeconds(7200)
//			.refreshTokenValiditySeconds(10000);
		clients.withClientDetails(clientDetails());
	}

	/**
	 * 用来配置授权authorization以及令牌token的访问端点和令牌服务token services
	 *
	 * @param endpoints 端点
	 * @throws Exception
	 */

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
		//token 储存器
		tokenServices.setTokenStore(tokenStore());
		//是否支持RefreshToken 默认false
		tokenServices.setSupportRefreshToken(true);
		//获取ClientDetailsService信息
		tokenServices.setClientDetailsService(clientDetails());
		//token 增强器
		tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
		// Accesstoken有效期
//		tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
		tokenServices.setAccessTokenValiditySeconds(30);
		endpoints.tokenServices(tokenServices)
		.authenticationManager(authenticationManager)
		.userDetailsService(userDetailsService)
		;
	}

	/**
	 * 用来配置令牌（token）端点的安全约束
	 *
	 * @param security
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 允许表单认证
		security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}

	/**
	 * 配置ClientDetails的获取模式
	 * 指定客户端信息处理类 为jdbc
	 */
	@Bean
	public JdbcClientDetailsService clientDetails() {
		JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
		jdbcClientDetailsService.setPasswordEncoder(new BCryptPasswordEncoder());
		return jdbcClientDetailsService;
	}

	/**
	 * 令牌储存器(持久化容器)
	 * OAuth2的永久令牌token管理主要交给TokenStore接口
	 * TokenStore管理OAuth2AccessToken 与OAuth2Authentication和OAuth2RefreshToken与OAuth2Authentication的对应关系的增删改查
	 * InMemoryTokenStore：将OAuth2AccessToken保存在内存(默认)
	 * JdbcTokenStore：将OAuth2AccessToken保存在数据库
	 * JwkTokenStore：将OAuth2AccessToken保存到JSON Web Key
	 * JwtTokenStore：将OAuth2AccessToken保存到JSON Web Token
	 * RedisTokenStore将OAuth2AccessToken保存到Redis
	 *
	 * @return
	 * @throws JoseException
	 */
	@Bean
	public TokenStore tokenStore() throws JoseException {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	/**
	 * JwtAccessTokenConverter JWT访问令牌转换器(token生成器)，按照设置的签名来生成Token
	 * <p>
	 * 注：JwtAccessTokenConverter实现了Token增强器TokenEnhancer接口和令牌转换器AccessTokenConverter接口
	 * JwtTokenStore类依赖JwtAccessTokenConverter类，授权服务器和资源服务器都需要接口的实现类（因此他们可以安全地使用相同的数据并进行解码）
	 *
	 * @return
	 * @throws JoseException
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() throws JoseException {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		String privateKeyStr = jwtProperties.getPrivateKeyStr();
		String publicKeyStr = jwtProperties.getPublicKeyStr();
		if (StringUtils.isAllBlank(privateKeyStr, publicKeyStr)) {
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


//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}


}
