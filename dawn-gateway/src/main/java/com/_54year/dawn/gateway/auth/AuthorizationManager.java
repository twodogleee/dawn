package com._54year.dawn.gateway.auth;

import com._54year.dawn.gateway.constant.GatewayConstant;
import com._54year.dawn.gateway.dao.DawnRoleResourcesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 鉴权管理器
 * 判断用户是否有资源的访问权限
 *
 * @author Andersen
 */
@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

	/**
	 * 角色与资源管理
	 */
	@Autowired
	DawnRoleResourcesMapper resourcesMapper;

	/**
	 * 鉴权
	 *
	 * @param mono                 链式响应
	 * @param authorizationContext 授权信息
	 * @return 响应
	 */
	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
		//获取Uri信息
		URI uri = authorizationContext.getExchange().getRequest().getURI();
		//用url查询到该资源需要的角色列表
		List<Map<String, Object>> roleList = resourcesMapper.selectRoleListByUrl(uri.getPath());
		List<String> authorities = roleList.stream()
			.map(role -> GatewayConstant.AUTHORITY_PREFIX + role.get("role_name")).collect(Collectors.toList());
		log.info(">>>>>当前url所需角色信息{}", authorities.toString());
		return mono
			.filter(Authentication::isAuthenticated)
			.flatMapIterable(Authentication::getAuthorities)
			//获取权限标识
			.map(GrantedAuthority::getAuthority)
			.any(role -> {
					log.info(">>>>>当前用户角色信息{}", role);
					//如果当前url没有配置访问权限则所有用户都可以访问 || 包含用户所拥有角色 || 来自服务器调用
					return CollectionUtils.isEmpty(authorities) || authorities.contains(role) || GatewayConstant.DAWN_SERVER_ROLE_NAME.equals(role);
				}
			)
			.map(AuthorizationDecision::new)
			.defaultIfEmpty(new AuthorizationDecision(false));
	}

}
