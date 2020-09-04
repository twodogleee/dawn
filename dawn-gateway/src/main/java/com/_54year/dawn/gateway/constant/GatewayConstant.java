package com._54year.dawn.gateway.constant;

import com._54year.dawn.core.constant.BasicConstant;

/**
 * 网关常量类
 *
 * @author Andersen
 */
public class GatewayConstant {

	public final static String TOKEN_NAME = "Authorization";
	public final static String EXTRAS_HEADER_KEY = BasicConstant.EXTRAS_HEADER_KEY;
	public static final String AUTHORITY_PREFIX = BasicConstant.AUTHORITY_PREFIX;
	public static final String AUTHORITY_CLAIM_NAME = BasicConstant.AUTHORITY_CLAIM_NAME;
	/**
	 * 需要放行的urlList
	 */
	public final static String[] PASS_URL_LIST = {
		"/dawn-auth/oauth/**",
		"/dawn-auth/user/**"
	};

}
