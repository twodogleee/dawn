package com._54year.dawn.basic.constant;

import java.util.HashMap;
import java.util.Map;

public class DawnResultMap {
	/**
	 * 成功
	 */
	public static final int SUCCESS_CODE = 1000;
	/**
	 * 失败
	 */
	public static final int FAIL_CODE = 2000;
	/**
	 * 业务异常
	 */
	public static final int SERVICE_ERR_CODE = 3000;
	/**
	 * 未知服务器异常
	 */
	public static final int UNKNOW_ERR_CODE = 4000;


	public static Map<String, Object> success() {
		return result(SUCCESS_CODE, "success", null);
	}

	public static Map<String, Object> success(Object data) {
		return result(SUCCESS_CODE, "success", data);
	}

	public static Map<String, Object> success(String msg, Object data) {
		return result(SUCCESS_CODE, msg, data);
	}

	public static Map<String, Object> fail() {
		return result(FAIL_CODE, "fail", null);
	}

	public static Map<String, Object> fail(String msg) {
		return result(FAIL_CODE, msg, null);
	}

	public static Map<String, Object> fail(String msg, Object data) {
		return result(FAIL_CODE, msg, data);
	}

	public static Map<String, Object> serviceErr(String msg) {
		return result(SERVICE_ERR_CODE, msg, null);
	}

	public static Map<String, Object> serviceErr(String msg, Object data) {
		return result(SERVICE_ERR_CODE, msg, data);
	}

	public static Map<String, Object> unknowErr() {
		return result(UNKNOW_ERR_CODE, "数据异常", null);
	}

	public static Map<String, Object> unknowErr(String msg) {
		return result(UNKNOW_ERR_CODE, msg, null);
	}

	public static Map<String, Object> unknowErr(String msg, Object data) {
		return result(UNKNOW_ERR_CODE, msg, data);
	}

	/**
	 * 封装Json格式的返回值
	 *
	 * @param status 状态值
	 * @param msg    消息
	 * @param data   数据
	 * @return 封装后的数据
	 */
	public static Map<String, Object> result(int status, String msg, Object data) {
		Map<String, Object> result = new HashMap<>();
		result.put("status", status);
		result.put("msg", msg);
		result.put("data", data);
		return result;
	}

}
