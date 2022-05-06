package com._54year.dawn.common.annotation;

import java.lang.annotation.*;

/**
 * 需要加密的数据字段
 *
 * @author Andersen
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EncryptData {

}
