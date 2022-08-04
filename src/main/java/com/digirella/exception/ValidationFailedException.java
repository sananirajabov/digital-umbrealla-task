package com.digirella.exception;


import com.digirella.exception.error.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.io.Serial;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ValidationFailedException extends RuntimeException implements HttpHandledException {

    @Serial
    private static final long serialVersionUID = 3805310188342105918L;

    private final ErrorResponse errorResponse;

    public ValidationFailedException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return BAD_REQUEST;
    }
}
