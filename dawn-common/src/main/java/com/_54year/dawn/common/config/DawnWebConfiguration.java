package com._54year.dawn.common.config;

import com._54year.dawn.common.handler.CurrentUserResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * springWebMvc相关配置
 *
 * @author Andersen
 */
@Configuration
@ConditionalOnWebApplication
public class DawnWebConfiguration implements WebMvcConfigurer {

	/**
	 * CurrentUser 注解参数解析器
	 *
	 * @return 参数解析器
	 */
	@Bean
	public CurrentUserResolver getCurrentUserResolver() {
		return new CurrentUserResolver();
	}

	/**
	 * 添加自定义webMvc入参解析器
	 *
	 * @param resolvers 解析器
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(getCurrentUserResolver());
	}
}
