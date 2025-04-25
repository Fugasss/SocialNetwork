package com.socialnetwork.authservice.controllers;

import com.socialnetwork.authservice.dto.AuthTokensPair;
import com.socialnetwork.authservice.dto.RefreshTokenRequest;
import com.socialnetwork.authservice.services.AuthTokenService;
import com.socialnetwork.shared.common.dto.UserCredentialsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final AuthTokenService tokenService;

    @GetMapping("/validate")
    public ResponseEntity<UserCredentialsDto> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String authToken = token.replace("Bearer ", "");

        if (!tokenService.isValidToken(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userId = tokenService.extractUserId(authToken);

        return ResponseEntity.ok(UserCredentialsDto.builder().id(userId).build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokensPair> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        String token = request.getRefreshToken();

        if (!tokenService.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AuthTokensPair newTokensPair = tokenService.refreshAuthTokensPair(token);

        return ResponseEntity.ok(newTokensPair);
    }
}
