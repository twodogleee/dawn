package com._54year.dawn.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "dawn.redis")
@Data
public class DawnRedisProperties {
	/**
	 * 是否使用集群连接 true集群 false单节点
	 */
	private boolean useNodes;
	/**
	 * 单节点 host
	 */
	private String hostName;
	/**
	 * 单节点端口
	 */
	private Integer port;

	/**
	 * 集群节点
	 */
	private List<String> nodes;
	/**
	 * 异常时最大重试次数
	 */
	private Integer maxAttempts;

	/**
	 * 密码
	 */
	private String password;
	/**
	 * 连接超时时间
	 */
	private Integer timeout;

	/**
	 * 最大空闲数
	 */
	private Integer maxIdle;

	/**
	 * 控制一个pool可分配多少个jedis实例,用来替换上面的redis.maxActive,如果是jedis 2.4以后用该属性
	 */
	private Integer maxTotal;

	/**
	 * 最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。
	 */
	private Integer maxWaitMillis;

	/**
	 * 连接的最小空闲时间 默认1800000毫秒(30分钟)
	 */
	private Integer minEvictableIdleTimeMillis;

	/**
	 * 每次释放连接的最大数目,默认3
	 */
	private Integer numTestsPerEvictionRun;

	/**
	 * 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程,默认-1
	 */
	private long timeBetweenEvictionRunsMillis;

	/**
	 * 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
	 */
	private boolean testOnBorrow;

	/**
	 * 在空闲时检查有效性, 默认false
	 */
	private boolean testWhileIdle;


}
