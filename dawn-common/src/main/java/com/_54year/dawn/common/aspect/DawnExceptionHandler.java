package com._54year.dawn.common.aspect;

import com._54year.dawn.core.constant.DawnResultMap;
import com._54year.dawn.core.excetion.DawnBusinessException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 统一异常处理
 * 通过@ControllerAdvice控制器增强与@ExceptionHandler实现
 *
 * @author Andersen
 */
@ControllerAdvice
@Order(1)
public class DawnExceptionHandler {
	@ResponseBody
	@ExceptionHandler(DawnBusinessException.class)
	public Map<String, Object> handleDawnBasicRuntimeException(DawnBusinessException dawnBusinessException) {
		return DawnResultMap.serviceErr(dawnBusinessException.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Map<String, Object> handleException(Exception e) {
		e.printStackTrace();
		return DawnResultMap.unknowErr();
	}

}
