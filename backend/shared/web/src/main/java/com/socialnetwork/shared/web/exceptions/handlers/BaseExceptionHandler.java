package com.socialnetwork.shared.web.exceptions.handlers;

import com.socialnetwork.shared.common.dto.MessageResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Slf4j
public abstract class BaseExceptionHandler {

    @Value("${debug}")
    private Boolean isDebug;

    protected ResponseEntity<MessageResponse> defaultExceptionHandling(final Exception exception, HttpStatus status) {
        log.error(exception.getMessage());

        MessageResponse message = isDebug ? MessageResponse.create(exception.getMessage()) : null;

        return ResponseEntity.status(status).body(message);
    }
}
