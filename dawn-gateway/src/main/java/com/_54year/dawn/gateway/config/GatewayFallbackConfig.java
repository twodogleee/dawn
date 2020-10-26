package com._54year.dawn.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * HystrixFallback 熔断降级配置
 *
 * @author Andersen
 */
@Configuration
public class GatewayFallbackConfig {
	/**
	 * 注入 hystrix熔断处理
	 */
	@Autowired
	private HystrixFallbackHandler hystrixFallbackHandler;

	/**
	 * 基础熔断路由配置
	 *
	 * @return 路由
	 */
	@Bean
	public RouterFunction routerFunction() {
		return RouterFunctions.route(
			RequestPredicates.GET("/defaultFallback")
				.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), hystrixFallbackHandler);
	}
}

