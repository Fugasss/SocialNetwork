package com.socialnetwork.userservice.exceptions.handlers;

import com.socialnetwork.shared.web.exceptions.handlers.BaseExceptionHandler;
import com.socialnetwork.userservice.exceptions.UserAlreadyExistsException;
import com.socialnetwork.userservice.exceptions.UserNotFoundException;
import com.socialnetwork.userservice.exceptions.WrongDataException;
import com.socialnetwork.shared.common.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionsHandler extends BaseExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<MessageResponse> handleNotFoundExceptions(final Exception exception) {
        return defaultExceptionHandling(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WrongDataException.class})
    public ResponseEntity<MessageResponse> handleBadRequestExceptions(final Exception exception) {
        return defaultExceptionHandling(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ObjectOptimisticLockingFailureException.class, UserAlreadyExistsException.class})
    public ResponseEntity<MessageResponse> handleConflictExceptions(final Exception exception) {
        return defaultExceptionHandling(exception, HttpStatus.CONFLICT);
    }
}
