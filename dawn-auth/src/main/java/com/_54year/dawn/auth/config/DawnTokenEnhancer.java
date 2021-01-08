package com._54year.dawn.auth.config;

import com._54year.dawn.auth.entity.DawnUser;
import com._54year.dawn.core.constant.BasicConstant;
import com._54year.dawn.core.constant.DawnRedisKeyConstant;
import com._54year.dawn.core.excetion.DawnBusinessException;
import com._54year.dawn.redis.util.RedisUtil;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JwtToken 负载数据增强
 *
 * @author Andersen
 */
public class DawnTokenEnhancer implements TokenEnhancer {

	/**
	 * 注入redis工具
	 */
	RedisUtil redisUtil;

	public DawnTokenEnhancer(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	/**
	 * 增强方法
	 *
	 * @param accessToken    token
	 * @param authentication 授权信息
	 * @return 增强后的token
	 */
	@SneakyThrows
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		/*
			如果授权模式为client_credentials为token添加authorities和role 防止被权限拦截
			所以client_credentials的认证模式 只能开放给服务端
		 */
		if ("client_credentials".equals(authentication.getOAuth2Request().getGrantType())) {
			List<String> role = Arrays.asList(BasicConstant.DAWN_SERVER_ROLE_NAME);
			info.put(BasicConstant.AUTHORITY_CLAIM_NAME, role);
			info.put(BasicConstant.ROLE_LIST_KEY, role);
		} else {
			//从authentication中获取当前登录用户信息
			DawnUser user = (DawnUser) authentication.getUserAuthentication().getPrincipal();
//		for (Field field : DawnUser.class.getDeclaredFields()) {
//			field.setAccessible(true);
//			info.put(field.getName(), field.get(user) != null ? field.get(user).toString() : "");
//		}
			info.put("user_id", user.getUserId());
			info.put("nick_name", user.getNickName());
			info.put(BasicConstant.ROLE_LIST_KEY, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
			saveToken2Redis(accessToken, authentication, user);
		}
		//设置附加信息
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

	/**
	 * 将token信息与用户绑定 做token的主动失效使用
	 *
	 * @param accessToken    token
	 * @param authentication 认证信息
	 * @param user           用户信息
	 */
	public void saveToken2Redis(OAuth2AccessToken accessToken, OAuth2Authentication authentication, DawnUser user) {
		String clientId = authentication.getOAuth2Request().getClientId();
		if (clientId == null || clientId.trim().isEmpty()) {
			throw new DawnBusinessException("未获取到CLIENT信息");
		} else {
			redisUtil.set(DawnRedisKeyConstant.DAWN_AUTH_TOKEN + user.getUserId() + DawnRedisKeyConstant.DAWN_REDIS_SEPARATOR_CHAR + clientId, accessToken.getValue());
		}
	}
}
