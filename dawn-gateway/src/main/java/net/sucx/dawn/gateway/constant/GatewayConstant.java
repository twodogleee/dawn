package net.sucx.dawn.gateway.constant;

import net.sucx.dawn.basic.constant.BasicConstant;

import java.util.Arrays;
import java.util.List;

/**
 * 网关常量类
 *
 * @author Andersen
 */
public class GatewayConstant {

	public final static String TOKEN_NAME = "Authorization";
	public final static String EXTRAS_HEADER_KEY = BasicConstant.EXTRAS_HEADER_KEY;
	/**
	 * 需要放行的urlList
	 */
	public final static List<String> PASS_URL_LIST = Arrays.asList(
		"/login",
		"/aaa",
		"/bbb"
	);

}
