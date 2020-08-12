package com._54year.dawn.core.result;

/**
 * 封装返回结果错误码
 * 所有code值 msg都需要集中管理并实现该类
 *
 * @author Andersen
 */
public interface DawnErrorCode {
	/**
	 * 获取code值
	 *
	 * @return code值
	 */
	int getCode();

	/**
	 * 获取msg
	 *
	 * @return 提示消息
	 */
	String getMessage();
}
