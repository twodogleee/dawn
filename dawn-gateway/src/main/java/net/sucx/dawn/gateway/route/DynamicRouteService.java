package net.sucx.dawn.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 动态路由处理，提供新增、更新、删除路由
 *
 * @author Andersen
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    /**
     * 事件发布者
     */
    private ApplicationEventPublisher publisher;


    /**
     * 增加路由
     *
     * @param definition 路由信息
     * @return msg
     */
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }


    /**
     * 更新路由
     *
     * @param definition 路由信息
     * @return msg
     */
    public String update(RouteDefinition definition) {
        try {
            //先删除路由
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
            //写入路由
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route fail";
        }


    }

    /**
     * 删除路由
     *
     * @param id 路由id
     * @return msg
     */
    public String delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            return "delete success";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }

    }


    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
