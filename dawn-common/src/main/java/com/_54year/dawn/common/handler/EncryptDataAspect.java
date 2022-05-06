package com._54year.dawn.common.handler;


import com._54year.dawn.common.annotation.EncryptData;
import com._54year.dawn.common.annotation.EncryptDataMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * 数据加密 切面
 *
 * @author Andersen
 */
@Aspect
@Order(99)
@Component
@Slf4j
public class EncryptDataAspect {
	/**
	 * 定义切点为携带EncryptDataMethod注解的方法
	 */
	@Pointcut("@annotation(com._54year.dawn.common.annotation.EncryptDataMethod)")
	public void annotationPointcut() {
	}

	/**
	 * 在切点之前执行一些业务
	 *
	 * @param joinPoint 切点
	 */
	@Before("annotationPointcut()&&@annotation(annotation)")
	public void beforePointcut(JoinPoint joinPoint, EncryptDataMethod annotation) {
		try {
			//获取请求参数
			Object[] params = joinPoint.getArgs();
			//获取加密类型
			String encryptType = annotation.value();
			for (Object object : params) {
				//如果参数为集合
				if (object instanceof Collection) {
					Collection collection = (Collection) object;
					collection.forEach(paramObj -> {
						try {
							//加密请求参数
							handleEncrypt(paramObj, encryptType);
						} catch (IllegalAccessException e) {
							log.error("加密数据异常");
						}
					});
				} else {
					//加密请求参数
					handleEncrypt(object, encryptType);
				}
			}
		} catch (Throwable throwable) {
			log.error("EncryptDataAspect-异常", throwable);
		}

	}

	/**
	 * 环绕通知
	 *
	 * @param joinPoint 切点
	 * @return 执行结果
	 * @throws Throwable 执行异常
	 */
	@Around("annotationPointcut()&&@annotation(annotation)")
	public Object doAround(ProceedingJoinPoint joinPoint, EncryptDataMethod annotation) throws Throwable {
//		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//		String[] params = methodSignature.getParameterNames();// 获取参数名称
//		Object[] args = joinPoint.getArgs();// 获取参数值
//		return joinPoint.proceed(args);
		return joinPoint.proceed();
	}

	/**
	 * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
	 *
	 * @param joinPoint 切点
	 */
	@AfterReturning("annotationPointcut()")
	public void doAfterReturning(JoinPoint joinPoint) {
	}

	/**
	 * 处理加密
	 *
	 * @param inputObj 入参
	 * @param type     加密类型
	 */
	private void handleEncrypt(Object inputObj, String type) throws IllegalAccessException {
		//如果入参为空
		if (ObjectUtils.isEmpty(inputObj)) {
			return;
		}
		//获取入参的所有属性值
		Field[] fields = inputObj.getClass().getDeclaredFields();
		//遍历属性值
		for (Field field : fields) {
			//判断该字段是否需要加密
			boolean encryptDataField = field.isAnnotationPresent(EncryptData.class);
			if (encryptDataField) {
				//访问安全检查的开关
				boolean accessible = field.isAccessible();
				//为false则true
				if (!accessible) {
					field.setAccessible(true);
				}
				field.set(inputObj, encrypt(field.get(inputObj), type));
				if (!accessible) {
					field.setAccessible(false);
				}
			}
		}
	}

	/**
	 * 加密方法
	 *
	 * @param fieldValue 原字段值
	 * @param type       加密类型
	 * @return 加密后的值
	 */
	private Object encrypt(Object fieldValue, String type) {
		if (ObjectUtils.isEmpty(fieldValue) || !(fieldValue instanceof String) || !"def".equals(type)) {
			return fieldValue;
		}
		return DigestUtils.md5DigestAsHex(String.valueOf(fieldValue).getBytes(StandardCharsets.UTF_8));
	}
}
