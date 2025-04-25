package com.javalab.gatewayservice.services;

import com.socialnetwork.shared.common.dto.UserCredentialsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String VALIDATE_TOKEN_PATH = "/validate";

    @Value("${spring.cloud.gateway.routes[0].uri}")
    private String authServiceUri;

    private final WebClient webClient;


    public Mono<String> validateTokenAndGetUserId(String token) {
        return webClient.get()
                .uri(authServiceUri + VALIDATE_TOKEN_PATH)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, response.logPrefix())))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, response.logPrefix())))
                .bodyToMono(UserCredentialsDto.class)
                .doOnSuccess(dto-> log.info("Received dto: {}", dto.toString()))
                .doOnError(error->log.error("An error has occurred {}", error.getMessage()))
                .onErrorResume(error -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage())))
                .map(UserCredentialsDto::getId);
    }
}
