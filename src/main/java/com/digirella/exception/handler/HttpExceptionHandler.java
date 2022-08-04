package com.digirella.exception.handler;

import com.digirella.exception.EntityAlreadyExistsException;
import com.digirella.exception.ValidationFailedException;
import com.digirella.exception.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serial;
import java.io.Serializable;

import static com.digirella.exception.error.GeneralErrorCode.ENTITY_ALREADY_EXISTS;
import static com.digirella.exception.error.GeneralErrorCode.VALIDATION_FAILED;

@Slf4j
@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<HttpErrorResponse> handleValidationFailedException(ValidationFailedException exception) {
        log.warn("Validation error occurred", exception);
        return new ResponseEntity<>(HttpErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(VALIDATION_FAILED)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<HttpErrorResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        log.warn("Entity already exists error occurred", exception);
        return new ResponseEntity<>(HttpErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(ENTITY_ALREADY_EXISTS)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class HttpErrorResponse implements Serializable {
        @Serial
        private static final long serialVersionUID = 1719414284750382140L;
        private ErrorCode errorCode;
        private String message;
    }
}
