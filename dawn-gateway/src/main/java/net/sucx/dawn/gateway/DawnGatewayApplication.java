package net.sucx.dawn.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//注册服务
@EnableDiscoveryClient

public class DawnGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawnGatewayApplication.class, args);
    }

}
