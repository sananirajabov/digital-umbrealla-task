package com.digirella.exception.error;

public enum GeneralErrorCode implements ErrorCode {

    ENTITY_NOT_FOUND,
    ENTITY_ALREADY_EXISTS,
    VALIDATION_FAILED,
    UNHANDLED_SERVICE_ERROR,
    INVALID_REQUEST_BODY,
    INVALID_AMOUNT,
    AUTHORIZATION_FAILED,
    AUTHENTICATION_FAILED;

    @Override
    public String getCode() {
        return this.name();
    }
}
