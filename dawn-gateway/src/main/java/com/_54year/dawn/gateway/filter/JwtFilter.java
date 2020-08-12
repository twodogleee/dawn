package com._54year.dawn.gateway.filter;

import com._54year.dawn.core.result.DawnResult;
import com._54year.dawn.gateway.constant.GatewayConstant;
import com._54year.dawn.jwt.service.JwkUtil;
import com._54year.dawn.jwt.service.JwtService;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Jwt鉴权 全局过滤器
 *
 * @author Andersen
 */
@Component
public class JwtFilter implements GlobalFilter, Ordered {

	private static Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
	/**
	 * jwt服务
	 */
	@Autowired
	private JwtService jwtService;

	/**
	 * 过滤器执行方法
	 *
	 * @param chain 过滤器链 用于过滤器的链式调用
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		String uri = request.getURI().getPath();
		//携带在header中
		String headerToken = request.getHeaders().getFirst(GatewayConstant.TOKEN_NAME);
		//携带在url中
		String paramToken = request.getQueryParams().getFirst(GatewayConstant.TOKEN_NAME);
		//携带中cookie中
		HttpCookie cookie = request.getCookies().getFirst(GatewayConstant.TOKEN_NAME);
		String cookieToken = cookie == null ? "" : cookie.getValue();
		String authorization;
		//先获取header
		if (StringUtils.isNotBlank(headerToken)) {
			authorization = headerToken;
		} else if (StringUtils.isNotBlank(paramToken)) {
			//获取url
			authorization = paramToken;
		} else if (StringUtils.isNotBlank(cookieToken)) {
			//获取cookie
			authorization = cookieToken;
		} else {
			return getErrResult(response);
		}
		try {
			JwtClaims claims = jwtService.parseToken(JwkUtil.getTokenStr(authorization));
			if (claims == null) {
				return getErrResult(response);
			}
			//将解析到的jwt附加内容添加到request的header中
			// 定义新的消息头
			HttpHeaders headers = new HttpHeaders();
			headers.putAll(request.getHeaders());
			headers.add(GatewayConstant.EXTRAS_HEADER_KEY, Base64Utils.encodeToString(claims.getRawJson().getBytes()));
			ServerHttpRequest newRequest = new ServerHttpRequestDecorator(request) {
				@Override
				public HttpHeaders getHeaders() {
					return headers;
				}
			};
			return chain.filter(exchange.mutate().request(newRequest).build());
		} catch (Exception e) {
			LOGGER.error("解析用户信息失败", e);
			return getErrResult(response);
		}
	}


	/**
	 * 从Flux<DataBuffer>中获取字符串的方法
	 *
	 * @return 请求体
	 */
	private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
		//获取请求体
		Flux<DataBuffer> body = serverHttpRequest.getBody();

		AtomicReference<String> bodyRef = new AtomicReference<>();
		body.subscribe(buffer -> {
			CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
			DataBufferUtils.release(buffer);
			bodyRef.set(charBuffer.toString());
		});
		//获取request body
		return bodyRef.get();
	}

	/**
	 * 将String封装成 dataBuffer
	 *
	 * @param value 内容
	 * @return dataBuffer
	 */
	private DataBuffer stringBuffer(String value) {
		byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
		NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
		DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
		buffer.write(bytes);
		return buffer;
	}


	/**
	 * 获取错误信息响应
	 *
	 * @param response 响应体
	 * @return 响应内容
	 */
	private Mono<Void> getErrResult(ServerHttpResponse response) {
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		String body = JSON.toJSONString(DawnResult.unauthorized());
		DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(buffer));

	}

	/**
	 * 执行优先级
	 *
	 * @return 优先级
	 */
	@Override
	public int getOrder() {
		//调高执行级别 >-1
		return -99;
	}
}
