package com._54year.dawn.common.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com._54year.dawn.common.annotation.HasRole;
import com._54year.dawn.common.constant.CommonConstant;
import com._54year.dawn.common.exception.DawnNoPermissionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过自定义角色注解进行角色认证
 *
 * @author Andersen
 */
@Aspect
@Order(99)
@Component
public class HasRoleAspect {
	/**
	 * 定义切入点 为注解
	 */
	@Pointcut("@annotation(com._54year.dawn.common.annotation.HasRole)")
	public void annotationPointcut() {

	}

	/**
	 * 在切点之前执行 一些业务
	 *
	 * @param joinPoint 切点
	 */
	@Before("annotationPointcut()&&@annotation(hasRole)")
	public void beforePointcut(JoinPoint joinPoint, HasRole hasRole) {
		// 此处进入到方法前  可以实现一些业务逻辑
		//获取request
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		//获取请求头中的附加内容
		String extras = request.getHeader(CommonConstant.EXTRAS_HEADER_KEY);
		if (extras == null || extras.trim().isEmpty()) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}
		extras = new String(Base64Utils.decodeFromString(extras));
		JSONObject userJson = JSON.parseObject(extras);
		if (!userJson.containsKey(CommonConstant.DAWN_ROLE_KEY)) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}
		String roleName = userJson.getString(CommonConstant.DAWN_ROLE_KEY);
		if (roleName == null || roleName.trim().isEmpty()) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}
		if (!CommonConstant.DAWN_SERVER_ROLE_NAME.equals(roleName) && !roleName.contains(hasRole.roleName())) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}

	}

	/**
	 * 环绕通知
	 *
	 * @param joinPoint 切点
	 * @return
	 * @throws Throwable
	 */
	@Around("annotationPointcut()&&@annotation(hasRole)")
	public Object doAround(ProceedingJoinPoint joinPoint, HasRole hasRole) throws Throwable {
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


}
