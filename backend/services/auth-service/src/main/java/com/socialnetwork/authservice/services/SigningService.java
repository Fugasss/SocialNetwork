package com.socialnetwork.authservice.services;

import com.socialnetwork.authservice.dto.AuthTokensPair;
import com.socialnetwork.authservice.dto.SignInRequest;
import com.socialnetwork.authservice.dto.SignUpRequest;
import com.socialnetwork.authservice.exceptions.UserAlreadyExistsException;
import com.socialnetwork.authservice.exceptions.WrongPasswordException;
import com.socialnetwork.authservice.exceptions.WrongUsernameException;

public interface SigningService {

    void createAccount(SignUpRequest request) throws UserAlreadyExistsException;

    AuthTokensPair loginIntoAccount(SignInRequest request) throws WrongUsernameException, WrongPasswordException;
}
