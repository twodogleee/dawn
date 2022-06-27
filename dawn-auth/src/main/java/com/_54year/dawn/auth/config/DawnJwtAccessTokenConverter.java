package com._54year.dawn.auth.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * JwtAccessTokenConverter也属于token增强类,处理的是OAuth2AccessToken
 * 对jwt加密类做代理
 * 可以在加密之前干一些自己的事
 *
 * @author Andersen
 */
@Deprecated
public class DawnJwtAccessTokenConverter extends JwtAccessTokenConverter {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		String tokenId = accessToken.getValue();
		Map<String, Object> map = accessToken.getAdditionalInformation();
		System.out.println(tokenId);
		System.out.println(map.toString());
		return super.enhance(accessToken, authentication);
	}
}

