package com._54year.dawn.mysql.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:dawn-db.yml")
public class DruidConfig {
//	@ConfigurationProperties(prefix = "spring.datasource")
//	@Bean
//	public DataSource druid() {
//		return new DruidDataSource();
//	}


}
