package com.socialnetwork.authservice.services.impl;

import com.socialnetwork.authservice.dto.AuthTokensPair;
import com.socialnetwork.authservice.exceptions.TokenGenerationException;
import com.socialnetwork.authservice.services.AuthTokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private static final String ISSUER = "auth-service";

    @Value("${security.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.access-token-expiration-seconds}")
    private Long ACCESS_TOKEN_EXPIRATION_SECONDS;

    @Value("${security.jwt.refresh-token-expiration-seconds}")
    private Long REFRESH_TOKEN_EXPIRATION_SECONDS;

    @Override
    public AuthTokensPair generateAuthTokensPair(String userId) {
        log.info("Generating auth tokens pair");

        AuthTokensPair tokensPair = new AuthTokensPair(
                generateToken(userId, ACCESS_TOKEN_EXPIRATION_SECONDS),
                generateToken(userId, REFRESH_TOKEN_EXPIRATION_SECONDS)
        );

        log.info("Tokens successfully generated");

        return tokensPair;
    }

    @Override
    public AuthTokensPair refreshAuthTokensPair(String refreshToken) {
        String userId = extractUserId(refreshToken);
        return generateAuthTokensPair(userId);
    }

    private String generateToken(String userId, Long expirationTimeSeconds) {
        try {
            JWSSigner signer = new MACSigner(SECRET_KEY.getBytes());
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(userId)
                    .issuer(ISSUER)
                    .expirationTime(Date.from(Instant.now().plusSeconds(expirationTimeSeconds)))
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (KeyLengthException e) {
            log.error(e.getMessage());
            throw new TokenGenerationException("Cannot generate token using provided secret key: %s".formatted(e.getMessage()));
        } catch (JOSEException e) {
            log.error(e.getMessage());
            throw new TokenGenerationException("Cannot generate token using provided signer: %s".formatted(e.getMessage()));
        }

    }

    public boolean isValidToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

            return signedJWT.verify(verifier) && !isTokenExpired(signedJWT);
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws ParseException {
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return expirationTime.before(new Date());
    }

    public String extractUserId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Error extracting user ID", e);
        }
    }


}
