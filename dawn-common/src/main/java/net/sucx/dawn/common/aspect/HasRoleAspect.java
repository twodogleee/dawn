package net.sucx.dawn.common.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sucx.dawn.common.annotation.HasRole;
import net.sucx.dawn.common.exception.DawnNoPermissionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

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
	@Pointcut("@annotation(net.sucx.dawn.common.annotation.HasRole)")
	public void annotationPointcut() {

	}

	/**
	 * 在切点之前执行 一些业务
	 *
	 * @param joinPoint 切点
	 */
	@Before("annotationPointcut()")
	public void beforePointcut(JoinPoint joinPoint) {
		// 此处进入到方法前  可以实现一些业务逻辑
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
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String[] params = methodSignature.getParameterNames();// 获取参数名称
		Object[] args = joinPoint.getArgs();// 获取参数值
		//获取request
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		//获取请求头中的附加内容
		String extras = request.getHeader("extras");
		if (extras == null || extras.trim().isEmpty()) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}
		extras = new String(Base64Utils.decodeFromString(extras));
		JSONObject userJson = JSON.parseObject(extras);
		if (!userJson.containsKey("role_name")) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}
		String roleName = userJson.getString("role_name");
		if (roleName == null || roleName.trim().isEmpty() || !roleName.contains(hasRole.roleName())) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}

		return joinPoint.proceed(args);
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
