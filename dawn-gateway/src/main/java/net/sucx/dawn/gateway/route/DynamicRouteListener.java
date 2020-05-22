package net.sucx.dawn.gateway.route;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import net.sucx.dawn.gateway.config.NacosGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 动态路由监听 实现CommandLineRunner 在项目启动后执行
 *
 * @author Andersen
 */
@Component
@Order(value = 1)
public class DynamicRouteListener implements CommandLineRunner {

    /**
     * nacos bootstrap中的配置信息
     */
    @Autowired
    private NacosConfigProperties nacosConfigProperties;
    /**
     * 动态路由业务类
     */
    @Autowired
    private DynamicRouteService dynamicRouteService;
    /**
     * 网关基础配置信息
     */
    @Autowired
    private NacosGatewayProperties nacosGatewayProperties;

    /**
     * 监听nacos的路由配置信息，并实现自动修改路由信息
     */
    public void addDynamicRouteListener() {
        try {
            //服务地址
            String nacosServer = nacosConfigProperties.getServerAddr();
            //路由列表id
            String dataId = nacosGatewayProperties.getRouteDataId();
            //配置群组
            String group = nacosConfigProperties.getGroup();
            //获取配置超时时间
            long timeout = nacosGatewayProperties.getGetRouteTimeOut();
            //获取配置服务
            ConfigService configService = NacosFactory.createConfigService(nacosServer);
            //获取路由列表
            String routeListStr = configService.getConfig(dataId, group, timeout);
            //初始化路由列表
            if (routeListStr != null && !routeListStr.trim().isEmpty()) {
                //将json路由列表转为路由实体并修改路由
                List<RouteDefinition> list = JSON.parseArray(routeListStr, RouteDefinition.class);
                list.forEach(definition -> {
                    dynamicRouteService.update(definition);
                });
            }
            //监听路由列表
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    //路由发送改变时 修改路由信息
                    List<RouteDefinition> list = JSON.parseArray(configInfo, RouteDefinition.class);
                    list.forEach(definition -> {
                        dynamicRouteService.update(definition);
                    });
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run(String... args) throws Exception {
        addDynamicRouteListener();
    }
}
