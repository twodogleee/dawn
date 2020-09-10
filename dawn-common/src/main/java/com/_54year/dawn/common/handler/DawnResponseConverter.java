package com._54year.dawn.common.handler;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

/**
 * 自定义返回结果消息解析器
 * 继承AbstractHttpMessageConverter抽象类
 * 通过DawnResultAspect已经实现了 这个留作一个解决方案
 * 这种参数解析器都需要配置到MVC里面取才能执行
 *
 * @author Andersen
 */
public class DawnResponseConverter extends AbstractHttpMessageConverter<Object> {


	@Override
	protected boolean supports(Class<?> clazz) {
		//是个Object就行
		return false;
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return null;
	}

	@Override
	protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

	}
}
