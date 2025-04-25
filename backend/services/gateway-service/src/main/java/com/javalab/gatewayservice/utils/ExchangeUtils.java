package com.javalab.gatewayservice.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

public final class ExchangeUtils {

    private ExchangeUtils() {
    }

    public static ServerWebExchange addHeader(final ServerWebExchange exchange, final String key, final String value) {
        ServerHttpRequest request = exchange.getRequest().mutate().header(key, value).build();
        return exchange.mutate().request(request).build();
    }
}
