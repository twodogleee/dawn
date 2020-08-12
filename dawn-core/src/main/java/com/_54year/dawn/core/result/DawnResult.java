package com._54year.dawn.core.result;

import java.io.Serializable;

/**
 * 通用返回结果
 *
 * @param <T> 数据类型
 */
public class DawnResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * code值
	 */
	private int code;
	/**
	 * 提示消息
	 */
	private String message;
	/**
	 * 数据
	 */
	private T data;

	private DawnResult(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 成功
	 *
	 * @param data 返回数据
	 * @param <T>  数据类型
	 * @return 统一返回结果
	 */
	public static <T> DawnResult<T> success(T data) {
		return new DawnResult<T>(DawnBasicResultCode.SUCCESS.getCode(), DawnBasicResultCode.SUCCESS.getMessage(), data);
	}

	/**
	 * 成功带自定义msg
	 *
	 * @param data    返回数据
	 * @param message 提示信息
	 * @param <T>     数据类型
	 * @return 返回结果
	 */
	public static <T> DawnResult<T> success(T data, String message) {
		return new DawnResult<T>(DawnBasicResultCode.SUCCESS.getCode(), message, data);
	}

	/**
	 * 自定义失败返回结果
	 *
	 * @param errorCode DawnErrorCode实现
	 * @param <T>       数据类型
	 * @return 返回结果
	 */
	public static <T> DawnResult<T> failed(DawnErrorCode errorCode) {
		return new DawnResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
	}

	/**
	 * 自定义失败返回结果
	 *
	 * @param errorCode code统一实现
	 * @param message   msg
	 * @param <T>       数据类型
	 * @return 返回结果
	 */
	public static <T> DawnResult<T> failed(DawnErrorCode errorCode, String message) {
		return new DawnResult<T>(errorCode.getCode(), message, null);
	}

	/**
	 * 基础失败返回结果
	 *
	 * @return 返回结果
	 */
	public static <T> DawnResult<T> failed() {
		return failed(DawnBasicResultCode.FAILED);
	}

	/**
	 * 参数验证失败返回结果
	 *
	 * @return 返回结果
	 */
	public static <T> DawnResult<T> validateFailed() {
		return failed(DawnBasicResultCode.VALIDATE_FAILED);
	}


	/**
	 * 未授权返回
	 *
	 * @return 返回结果
	 */
	public static <T> DawnResult<T> unauthorized() {
		return unauthorized(null);
	}

	/**
	 * 未授权返回
	 */
	public static <T> DawnResult<T> unauthorized(T data) {
		return new DawnResult<T>(DawnBasicResultCode.UNAUTHORIZED.getCode(), DawnBasicResultCode.UNAUTHORIZED.getMessage(), data);
	}

	/**
	 * 没有相关访问权限返回
	 *
	 * @return 返回结果
	 */
	public static <T> DawnResult<T> forbidden() {
		return forbidden(null);
	}

	/**
	 * 没有相关访问权限返回
	 *
	 * @return 返回结果
	 */
	public static <T> DawnResult<T> forbidden(T data) {
		return new DawnResult<T>(DawnBasicResultCode.FORBIDDEN.getCode(), DawnBasicResultCode.FORBIDDEN.getMessage(), data);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
