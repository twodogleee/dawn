package com._54year.dawn.gateway.handler;


import com._54year.dawn.core.result.DawnResult;
import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 未授权处理
 *
 * @author Andersen
 */
@Component
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		String body = JSON.toJSONString((DawnResult.unauthorized(e.getMessage())));
		DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(buffer));
	}
}
