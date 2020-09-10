package com._54year.dawn.common.handler;

import com._54year.dawn.common.exception.DawnNoPermissionException;
import com._54year.dawn.core.enums.DawnBasicResultCode;
import com._54year.dawn.core.excetion.DawnBusinessException;
import com._54year.dawn.core.result.impl.DawnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 * 通过@ControllerAdvice控制器增强与@ExceptionHandler实现
 *
 * @author Andersen
 */
@ControllerAdvice
@Order(1)
public class DawnExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DawnExceptionHandler.class);


	@ResponseBody
	@ExceptionHandler(DawnNoPermissionException.class)
	public DawnResult<Object> handleDawnNoPermissionException(DawnNoPermissionException dawnNoPermissionException) {
		return DawnResult.failed(DawnBasicResultCode.FORBIDDEN, dawnNoPermissionException.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(DawnBusinessException.class)
	public DawnResult<Object> handleDawnBasicRuntimeException(DawnBusinessException dawnBusinessException) {
		return DawnResult.failed(DawnBasicResultCode.BUSINESS_ERR, dawnBusinessException.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public DawnResult<Object> handleException(Exception e) {
		LOGGER.error(">>>>>>>>>ServerError", e);
		return DawnResult.failed();
	}

}
