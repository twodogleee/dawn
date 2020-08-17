package com._54year.dawn.common.aspect;

import com._54year.dawn.core.result.ResultReaderFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 通过自定义注解进行结果封装
 *
 * @author Andersen
 */
@Aspect
@Order(1)
@Component
public class DawnResultAspect {
	/**
	 * 定义切入点 为注解
	 */
	@Pointcut("@annotation(com._54year.dawn.common.annotation.DawnResult)")
	public void annotationPointcut() {

	}


	/**
	 * 环绕通知
	 *
	 * @param joinPoint 切点
	 * @return
	 * @throws Throwable
	 */
	@Around("annotationPointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//		String[] params = methodSignature.getParameterNames();// 获取参数名称
		Object[] args = joinPoint.getArgs();// 获取参数值
		Object result = joinPoint.proceed(args);
		return ResultReaderFactory.getResultReader(result).load(result);
	}


}
