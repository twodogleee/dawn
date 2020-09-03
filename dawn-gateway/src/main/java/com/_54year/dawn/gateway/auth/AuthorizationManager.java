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
 * 鉴权管理器，用于判断是否有资源的访问权限
 * Created by macro on 2020/6/19.
 */
@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

	@Autowired
	DawnRoleResourcesMapper resourcesMapper;

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
		//从Redis中获取当前路径可访问角色列表
		URI uri = authorizationContext.getExchange().getRequest().getURI();
		List<Map<String, Object>> roleList = resourcesMapper.selectRoleListByUrl(uri.getPath());
		List<String> authorities = roleList.stream()
			.map(role -> GatewayConstant.AUTHORITY_PREFIX + role.get("role_name")).collect(Collectors.toList());
		//认证通过且角色匹配的用户可访问当前路径
		log.info(">>>>>当前url角色信息{}", authorities.toString());
		return mono
			.filter(Authentication::isAuthenticated)
			.flatMapIterable(Authentication::getAuthorities)
			.map(GrantedAuthority::getAuthority)
			.any(role -> {
					log.info(">>>>>当前用户角色信息{}", role);
					return CollectionUtils.isEmpty(authorities) || authorities.contains(role);
				}
			)
			.map(AuthorizationDecision::new)
			.defaultIfEmpty(new AuthorizationDecision(false));
	}

}
