package com._54year.dawn.excel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * excel相关服务启动类
 *
 * @author Andersen
 */
@SpringBootApplication(scanBasePackages = "com._54year.dawn.*")
//注册服务
@EnableDiscoveryClient
//注册feign客户端
@EnableFeignClients
public class DawnExcelApplication {

	public static void main(String[] args) {
		SpringApplication.run(DawnExcelApplication.class, args);
	}

}
