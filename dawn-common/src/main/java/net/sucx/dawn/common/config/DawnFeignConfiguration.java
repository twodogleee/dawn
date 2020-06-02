package net.sucx.dawn.common.config;

import com.alibaba.fastjson.JSONObject;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import net.sucx.dawn.common.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

/**
 * 服务端之间的feign配置 防止服务器之间的调用被HasRole拦截
 *
 * @author Andersen
 */
@Configuration
public class DawnFeignConfiguration {

	@Bean
	public ServerRoleRequestInterceptor serverRoleRequestInterceptor() {
		return new ServerRoleRequestInterceptor();
	}

	/**
	 * 实现feign的请求拦截器 添加服务器调用时添加extras请求头 用于HasRole进行权限认证
	 *
	 * @author Andersen
	 */
	public class ServerRoleRequestInterceptor implements RequestInterceptor {
		@Override
		public void apply(RequestTemplate requestTemplate) {
			JSONObject extras = new JSONObject(1);
			extras.put(CommonConstant.DAWN_ROLE_KEY, CommonConstant.DAWN_SERVER_ROLE_NAME);
			//添加附加的header信息
			requestTemplate.header(CommonConstant.EXTRAS_HEADER_KEY, Base64Utils.encodeToString(extras.toJSONString().getBytes()));
		}
	}
}
