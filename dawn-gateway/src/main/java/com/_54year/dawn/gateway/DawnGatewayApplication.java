package com._54year.dawn.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com._54year.dawn.*")
@MapperScan("com._54year.dawn.*.dao")
//注册服务
@EnableDiscoveryClient
public class DawnGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawnGatewayApplication.class, args);
    }

}
