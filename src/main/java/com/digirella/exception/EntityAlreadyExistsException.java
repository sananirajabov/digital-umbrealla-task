package com.digirella.exception;

import com.digirella.exception.error.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class EntityAlreadyExistsException extends RuntimeException implements HttpHandledException {

    @Serial
    private static final long serialVersionUID = 3805310542342105918L;

    private final ErrorResponse errorResponse;

    public EntityAlreadyExistsException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
