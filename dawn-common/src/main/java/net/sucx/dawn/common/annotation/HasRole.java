package net.sucx.dawn.common.annotation;

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
	String roleName();
}
