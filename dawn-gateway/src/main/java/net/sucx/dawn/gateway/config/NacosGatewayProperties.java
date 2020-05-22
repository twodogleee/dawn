package net.sucx.dawn.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 网关基础配置信息
 *
 * @author Andersen
 */
@Data
@Configuration
public class NacosGatewayProperties {
    /**
     * 路由列表配置id
     */
    @Value("${dawn.gateway.routeDataId}")
    private String routeDataId;
    /**
     * 获取配置超时时间
     */
    @Value("${dawn.gateway.getRouteTimeOut:3000}")
    private Long getRouteTimeOut;
}
