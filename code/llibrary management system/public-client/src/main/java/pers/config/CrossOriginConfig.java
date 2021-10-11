package pers.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * description: 跨域访问
 *
 * @author wangpeng
 * @date 2018/09/25
 */
@Component
public class CrossOriginConfig implements GlobalFilter, Ordered {
	protected static final String ALL = "*";
	protected static final String MAX_AGE = "18000L";

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
		ServerHttpRequest request = serverWebExchange.getRequest();
//        if (!CorsUtils.isCorsRequest(request)) {
//            return gatewayFilterChain.filter(serverWebExchange);
//        }
		ServerHttpResponse response = serverWebExchange.getResponse();
		HttpHeaders headers = response.getHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, OPTIONS, DELETE, PATCH");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
//		headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
		if (request.getMethod() == HttpMethod.OPTIONS) {
			response.setStatusCode(HttpStatus.OK);
			return Mono.empty();
		}
		return gatewayFilterChain.filter(serverWebExchange);
	}

	@Override
	public int getOrder() {
		return -300;
	}
}