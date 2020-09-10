package com._54year.dawn.common.annotation;

import java.lang.annotation.*;

/**
 * 角色判断注解
 *
 * @author Andersen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HasRole {
	/**
	 * 获取角色标识
	 * @return 标识
	 */
	String value();
}
