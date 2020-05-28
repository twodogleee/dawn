package net.sucx.dawn.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "net.sucx.dawn.*")
//注册服务
@EnableDiscoveryClient
//注册feign客户端
@EnableFeignClients
public class DawnAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawnAdminApplication.class, args);
    }

}
