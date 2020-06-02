package net.sucx.dawn.common.aspect;

import net.sucx.dawn.basic.constant.DawnResultMap;
import net.sucx.dawn.basic.excetion.DawnBasicRuntimeException;
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
	@ExceptionHandler(DawnBasicRuntimeException.class)
	public Map<String, Object> handleDawnBasicRuntimeException(DawnBasicRuntimeException dawnBasicRuntimeException) {
		return DawnResultMap.serviceErr(dawnBasicRuntimeException.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Map<String, Object> handleException(Exception e) {
		e.printStackTrace();
		return DawnResultMap.unknowErr();
	}

}
