package com._54year.dawn.common.config;

import com._54year.dawn.common.handler.CurrentUserResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义BeanPostProcessor
 * 主要解决在有自定义参数解析器时
 * HandlerMethodArgumentResolver#getArgumentResolver在确定解析器时是循环调用,如果有一个解析器符合则break不会往下执行
 * 所以当自定义参数解析器的顺序与其他参数解析器冲突时由于加载顺序可能导致自定义的解析器不生效
 * (目前没有使用,留作学习和了解;如果使用了WebMvcConfigurer添加解析器就最好不要再用该方法添加,两种只保留一种)
 *
 * @author Andersen
 */
//@Configuration
@Deprecated
public class ResolverBeanPostProcessor implements BeanPostProcessor {

	/**
	 * 在初始化bean之后处理
	 *
	 * @param bean     bean实体
	 * @param beanName bean名称
	 * @return bean
	 * @throws BeansException bean异常
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("-------------------------------" + beanName);
		if (beanName.equals("requestMappingHandlerAdapter")) {
			//requestMappingHandlerAdapter进行修改
			RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
			//获取RequestMappingHandlerAdapter中的参数解析器
			List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();
			//添加自定义参数处理器
			argumentResolvers = addArgumentResolvers(argumentResolvers);
			adapter.setArgumentResolvers(argumentResolvers);
		}
		return bean;
	}

	/**
	 * 添加参数解析器
	 * 把自定义的参数解析器添加在最前面 保证在遍历的时候最先被执行
	 *
	 * @param argumentResolvers 参数解析器集合
	 * @return 添加了自定义的参数解析器集合
	 */
	private List<HandlerMethodArgumentResolver> addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
		//将自定的添加到最前面
		resolvers.add(new CurrentUserResolver());
		//将原本的添加后面
		resolvers.addAll(argumentResolvers);
		return resolvers;
	}

}
