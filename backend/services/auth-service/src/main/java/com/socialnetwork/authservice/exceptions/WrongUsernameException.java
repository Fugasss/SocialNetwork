package com.socialnetwork.authservice.exceptions;

public class WrongUsernameException extends RuntimeException {
    public WrongUsernameException(String message) {
        super(message);
    }
}
