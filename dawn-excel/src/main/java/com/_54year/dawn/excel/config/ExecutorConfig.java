package com._54year.dawn.excel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Excel线程池配置
 *
 * @author Andersen
 */
@Slf4j
@Configuration
public class ExecutorConfig {
	/**
	 * 数据处理线程池
	 *
	 * @return 线程池
	 */
	@Bean
	public ThreadPoolTaskExecutor handleDataExecutor() {
		log.info("初始化Excel数据处理异步线程池 handleDataExecutor");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("handleDataExecutor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		executor.initialize();
		return executor;
	}

	/**
	 * Excel渲染线程池
	 *
	 * @return 线程池
	 */
	@Bean
	public ThreadPoolTaskExecutor writeDataExecutor() {
		log.info("初始化Excel渲染异步线程池 writeDataExecutor");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("writeDataExecutor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		executor.initialize();
		return executor;
	}
}
