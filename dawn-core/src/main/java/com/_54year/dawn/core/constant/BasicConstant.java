package com._54year.dawn.core.constant;

/**
 * 基础常量
 *
 * @author Andersen
 */
public class BasicConstant {
	/**
	 * header中用户信息 附加内容 请求头
	 */
	public final static String EXTRAS_HEADER_KEY = "user";
	/**
	 * token中权限标识key
	 */
	public static final String AUTHORITY_CLAIM_NAME = "authorities";
	/**
	 * 鉴权时 标识开头
	 */
	public static final String AUTHORITY_PREFIX = "ROLE_";
	/**
	 * 角色key集合
	 */
	public static final String ROLE_LIST_KEY = "role_list";
	/**
	 * token 请求头key
	 */
	public final static String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * 后台服务 角色名
	 */
	public final static String DAWN_SERVER_ROLE_NAME = "dawn_system";

}
