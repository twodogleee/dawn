package com._54year.dawn.common.annotation;

import java.lang.annotation.*;

/**
 * 需要拦截进行加密的方法
 *
 * @author Andersen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EncryptDataMethod {

	/**
	 * 加密方式
	 *
	 * @return 标识
	 */
	String value();
}
