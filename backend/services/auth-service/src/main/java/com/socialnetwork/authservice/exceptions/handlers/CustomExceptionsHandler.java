package com.socialnetwork.authservice.exceptions.handlers;

import com.socialnetwork.authservice.exceptions.TokenGenerationException;
import com.socialnetwork.authservice.exceptions.UserAlreadyExistsException;
import com.socialnetwork.authservice.exceptions.WrongPasswordException;
import com.socialnetwork.authservice.exceptions.WrongUsernameException;
import com.socialnetwork.shared.common.dto.MessageResponse;
import com.socialnetwork.shared.web.exceptions.handlers.BaseExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionsHandler extends BaseExceptionHandler {

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<MessageResponse> handleConflictException(final Exception e) {
        return defaultExceptionHandling(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({WrongUsernameException.class, WrongPasswordException.class})
    public ResponseEntity<MessageResponse> handleWrongDataException(final Exception e) {
        return defaultExceptionHandling(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TokenGenerationException.class})
    public ResponseEntity<MessageResponse> handleInternalErrorException(final Exception e) {
        return defaultExceptionHandling(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
