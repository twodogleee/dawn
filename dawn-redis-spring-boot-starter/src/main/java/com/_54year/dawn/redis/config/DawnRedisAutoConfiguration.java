package com._54year.dawn.redis.config;

import com._54year.dawn.redis.util.FastJson2JsonRedisSerializer;
import com._54year.dawn.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * redis自动装载配置类
 */
@Configuration
//@ConditionalOnWebApplication //如果应用是web时才进行生效（配置）
@EnableConfigurationProperties(DawnRedisProperties.class)
public class DawnRedisAutoConfiguration {

	@Autowired
	DawnRedisProperties dawnRedisProperties;


	/**
	 * 连接池配置信息
	 *
	 * @return JedisPoolConfig 连接池配置
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//最大空闲数
		jedisPoolConfig.setMaxIdle(dawnRedisProperties.getMaxIdle());
		//连接池最大连接数
		jedisPoolConfig.setMaxTotal(dawnRedisProperties.getMaxTotal());
		//最大建立连接等待时间
		jedisPoolConfig.setMaxWaitMillis(dawnRedisProperties.getMaxWaitMillis());
		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		jedisPoolConfig.setMinEvictableIdleTimeMillis(dawnRedisProperties.getMinEvictableIdleTimeMillis());
		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		jedisPoolConfig.setNumTestsPerEvictionRun(dawnRedisProperties.getNumTestsPerEvictionRun());
		//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(dawnRedisProperties.getTimeBetweenEvictionRunsMillis());
		//是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
		jedisPoolConfig.setTestOnBorrow(dawnRedisProperties.isTestOnBorrow());
		//在空闲时检查有效性, 默认false
		jedisPoolConfig.setTestWhileIdle(dawnRedisProperties.isTestWhileIdle());
		return jedisPoolConfig;
	}

	/**
	 * 创建redis连接工厂
	 *
	 * @return 连接工厂
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		//获取默认客户端并配置连接池
		JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder()
			.usePooling().poolConfig(jedisPoolConfig())
			.and().readTimeout(Duration.ofMillis(dawnRedisProperties.getTimeout()))
			.build();
		if (dawnRedisProperties.isUseNodes()) {
			/*
		集群模式
		 */
			RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(dawnRedisProperties.getNodes());
			clusterConfiguration.setMaxRedirects(dawnRedisProperties.getMaxAttempts());
			clusterConfiguration.setPassword(RedisPassword.of(dawnRedisProperties.getPassword()));
			//集群 + 客户端配置
			return new JedisConnectionFactory(clusterConfiguration, clientConfiguration);
		} else {
			/*
		单机模式
		 */
			//单机配置
			RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
			standaloneConfiguration.setHostName(dawnRedisProperties.getHostName());
			standaloneConfiguration.setPort(dawnRedisProperties.getPort());
			//database
//			standaloneConfiguration.setDatabase(database);
			standaloneConfiguration.setPassword(RedisPassword.of(dawnRedisProperties.getPassword()));
			//单机配置 + 客户端配置
			return new JedisConnectionFactory(standaloneConfiguration, clientConfiguration);
		}
		// 哨兵redis
		// RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
	}

	/**
	 * 实例化 RedisTemplate对象
	 *
	 * @return RedisTemplate对象
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		//如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
		// 开启事务
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}


	/**
	 * 创建序列化类
	 *
	 * @return 序列化类
	 */
	@Bean
	public RedisSerializer<Object> fastJson2JsonRedisSerializer() {
		return new FastJson2JsonRedisSerializer<>(Object.class);
	}


	/**
	 * 创建Redis工具类
	 *
	 * @return redis工具
	 */
	@Bean(name = "redisUtil")
	public RedisUtil redisUtil() {
		RedisUtil redisUtil = new RedisUtil();
		redisUtil.setRedisTemplate(redisTemplate());
		return redisUtil;
	}


}

