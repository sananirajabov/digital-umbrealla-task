package com.digirella.exception;

import com.digirella.exception.error.ErrorResponse;
import org.springframework.http.HttpStatus;

public interface HttpHandledException {

    ErrorResponse getErrorResponse();

    HttpStatus getHttpStatus();
}
