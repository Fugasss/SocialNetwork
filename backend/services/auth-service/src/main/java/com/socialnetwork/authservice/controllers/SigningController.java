package com.socialnetwork.authservice.controllers;

import com.socialnetwork.authservice.dto.AuthTokensPair;
import com.socialnetwork.authservice.dto.SignInRequest;
import com.socialnetwork.authservice.dto.SignUpRequest;
import com.socialnetwork.authservice.services.SigningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SigningController {

    private final SigningService signingService;

    @PostMapping("/register")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request) {
        log.info("Creating account: {}", request);
        signingService.createAccount(request);
        log.info("Account created: {}", request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokensPair> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("Signing in: {}", request.getEmail());
        AuthTokensPair tokens = signingService.loginIntoAccount(request);
        log.info("Signed in");

        return ResponseEntity.ok(tokens);
    }
}
