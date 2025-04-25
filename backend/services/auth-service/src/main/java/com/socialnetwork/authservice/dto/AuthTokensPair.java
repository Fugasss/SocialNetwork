package com.socialnetwork.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokensPair {
    private String accessToken;
    private String refreshToken;
}
