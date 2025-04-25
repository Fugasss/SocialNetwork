package com.socialnetwork.authservice.services.impl;

import com.socialnetwork.authservice.dto.AuthTokensPair;
import com.socialnetwork.authservice.dto.SignInRequest;
import com.socialnetwork.authservice.dto.SignUpRequest;
import com.socialnetwork.authservice.exceptions.UserAlreadyExistsException;
import com.socialnetwork.authservice.exceptions.UserCreationException;
import com.socialnetwork.authservice.exceptions.WrongPasswordException;
import com.socialnetwork.authservice.exceptions.WrongUsernameException;
import com.socialnetwork.authservice.services.AuthTokenService;
import com.socialnetwork.authservice.services.SigningService;
import com.socialnetwork.authservice.services.UserService;
import com.socialnetwork.shared.common.dto.UserCredentialsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SigningServiceImpl implements SigningService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    @Override
    public void createAccount(SignUpRequest request) throws UserAlreadyExistsException {

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserCredentialsDto data = UserCredentialsDto.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .build();

        ResponseEntity<UserCredentialsDto> response = userService.createUser(data);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new UserCreationException("User creation failed with status " + response.getStatusCode());
        }
    }

    @Override
    public AuthTokensPair loginIntoAccount(SignInRequest request) throws WrongUsernameException, WrongPasswordException {

        ResponseEntity<UserCredentialsDto> userResponse = userService.getUserByEmail(request.getEmail());

        if(!userResponse.getStatusCode().is2xxSuccessful() || !userResponse.hasBody()) {
            throw new WrongUsernameException("User not found");
        }

        UserCredentialsDto user = userResponse.getBody();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }

        return authTokenService.generateAuthTokensPair(user.getId());
    }
}