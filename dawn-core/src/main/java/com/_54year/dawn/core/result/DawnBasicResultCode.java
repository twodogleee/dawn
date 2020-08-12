package com._54year.dawn.core.result;

/**
 * 封装常用的返回值
 *
 * @author Andersen
 */
public enum DawnBasicResultCode implements DawnErrorCode {
	SUCCESS(200, "success"),
	FAILED(500, "数据异常"),
	VALIDATE_FAILED(404, "缺少传入参数或传入参数为空"),
	UNAUTHORIZED(401, "请求未授权或Token已过期"),
	FORBIDDEN(403, "没有访问权限");


	/**
	 * 状态码
	 */
	private int code;
	/**
	 * 提示信息
	 */
	private String message;

	DawnBasicResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 获取code
	 *
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 获取msg
	 *
	 * @return msg
	 */
	public String getMessage() {
		return message;
	}
}
