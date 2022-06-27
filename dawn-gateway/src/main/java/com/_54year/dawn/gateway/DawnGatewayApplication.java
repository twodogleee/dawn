package com._54year.dawn.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com._54year.dawn.*",
	//排除SpringGateway中的Lettuce Redis相关依赖,该依赖导致与我们自己的Redis包冲突
	exclude = {
		RedisAutoConfiguration.class,
		RedisRepositoriesAutoConfiguration.class}
)
//注册服务
@EnableDiscoveryClient
public class DawnGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DawnGatewayApplication.class, args);
	}

}
