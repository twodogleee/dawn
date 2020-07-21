package com._54year.dawn.common.annotation;

import java.lang.annotation.*;

/**
 * 封装返回结果注解
 *
 * @author Andersen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DawnResult {
}
