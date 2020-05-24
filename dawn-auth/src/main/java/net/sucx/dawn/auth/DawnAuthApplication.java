package net.sucx.dawn.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "net.sucx.dawn.*")
//注册服务
@EnableDiscoveryClient
public class DawnAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawnAuthApplication.class, args);
    }

}
