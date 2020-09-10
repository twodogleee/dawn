package com._54year.dawn.common.handler;

import com._54year.dawn.common.annotation.RequestUser;
import com._54year.dawn.common.constant.CommonConstant;
import com._54year.dawn.common.entity.CurrentUser;
import com._54year.dawn.common.exception.DawnNoPermissionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义参数解析器 绑定调用接口的当前用户信息
 *
 * @author Andersen
 */
//@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {
	/**
	 * 是否能够解析 调用处 HandlerMethodArgumentResolver#getArgumentResolver
	 * <p>
	 * 在第一次调用接口的时候Spring会加载解析器通过HandlerMethodArgumentResolver#getArgumentResolver
	 * 循环调用解析器的supportsParameter方法如果其中有一个解析返回true则break结束循环并将解析器添加至argumentResolverCache中
	 * 后面的调用不再循环调用而直接从argumentResolverCache中取
	 *
	 * @param parameter 解析参数
	 * @return 是否能够解析
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
//		log.info(">>>>>当前参数:{}",parameter);
//		log.info(">>>>>注解:{},boolean:{}",parameter.getParameterAnnotations(),parameter.hasParameterAnnotation(DawnUser.class));
//		log.info(">>>>>类型:{},boolean:{}",parameter.getParameterType(),parameter.getParameterType().isAssignableFrom(CurrentUser.class));
		//如果需要解析的参数type为CurrentUser && 参数携带DawnUser注解 则支持解析
		return parameter.getParameterType().isAssignableFrom(CurrentUser.class) && parameter.hasParameterAnnotation(RequestUser.class);
	}

	/**
	 * 具体解析方法
	 *
	 * @param parameter     入参
	 * @param mavContainer
	 * @param webRequest    请求参数
	 * @param binderFactory
	 * @return
	 * @throws Exception 声明异常
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String user = webRequest.getHeader(CommonConstant.EXTRAS_HEADER_KEY);
		if (user == null || user.trim().isEmpty()) {
			throw new DawnNoPermissionException("登录信息失效,请重新登录!");
		}
		//解析用户信息
		user = new String(Base64Utils.decodeFromString(user));
		JSONObject userJson = JSON.parseObject(user);
		return JSONObject.toJavaObject(userJson, CurrentUser.class);
	}
}
