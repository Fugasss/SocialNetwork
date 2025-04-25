package com.socialnetwork.authservice.services;

import com.socialnetwork.authservice.dto.AuthTokensPair;

public interface AuthTokenService {

    AuthTokensPair generateAuthTokensPair(String userId);

    AuthTokensPair refreshAuthTokensPair(String refreshToken);

    boolean isValidToken(String token);

    String extractUserId(String token);
}
