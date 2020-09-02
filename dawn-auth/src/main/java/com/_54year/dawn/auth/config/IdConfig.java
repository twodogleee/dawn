package com._54year.dawn.auth.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ID生成器配置
 *
 * @author Andersen
 */
@Configuration
public class IdConfig {
	/**
	 * 注入MybatisPlus提供的默认ID生成器
	 *
	 * @return id生成器
	 */
	@Bean
	public IdentifierGenerator identifierGenerator() {
		//nextID 使用雪花算法生成的ID
		return new DefaultIdentifierGenerator();
	}

}
