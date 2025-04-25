package com.javalab.gatewayservice.filters;

import com.javalab.gatewayservice.services.AuthService;
import com.socialnetwork.shared.common.web.CustomHttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.javalab.gatewayservice.utils.ExchangeUtils.addHeader;


@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/v3/api-docs",
            "/swagger-ui.html",
            "/swagger-ui/"
    );

    private final AuthService authService;

    public JwtAuthenticationFilter(@Lazy AuthService authService) {
        super(Config.class);
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String path = request.getPath().value();

            log.info("Request path: {}", path);

            if (EXCLUDED_PATHS.stream().anyMatch(path::startsWith)) {
                log.info("Excluding path: {}", path);
                return chain.filter(exchange);
            }

            HttpHeaders headers = request.getHeaders();

            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return failedAuthorizationResponse(exchange, new RuntimeException("Authorization header is missing"));
            }
            String token = headers.getFirst(HttpHeaders.AUTHORIZATION).substring("Bearer ".length());

            return authService.validateTokenAndGetUserId(token)
                    .map(userId -> addHeader(exchange, CustomHttpHeaders.X_USER_ID, userId))
                    .flatMap(chain::filter)
                    .onErrorResume(e -> failedAuthorizationResponse(exchange, e));
        };
    }

    private static Mono<Void> failedAuthorizationResponse(ServerWebExchange exchange, Throwable e) {
        log.error("FAILURE AUTHORIZATION! {}", e.getMessage());

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}