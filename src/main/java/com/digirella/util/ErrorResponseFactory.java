package com.digirella.util;

import com.digirella.exception.error.ErrorResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static com.digirella.exception.error.GeneralErrorCode.*;
import static java.lang.String.format;

@Slf4j
@UtilityClass
public class ErrorResponseFactory {

    public static ErrorResponse buildEntityAlreadyExistsWithGivenDateError(String date) {
        String message = format("Currencies already saved with given date [%s]", date);
        log.warn(message);

        return ErrorResponse.builder()
                .errorCode(ENTITY_ALREADY_EXISTS)
                .message(message)
                .build();
    }

    public static ErrorResponse buildInvalidDateFormatError() {
        String message = "Requested date should mot be null or empty";
        log.warn(message);

        return ErrorResponse.builder()
                .errorCode(INVALID_REQUEST_BODY)
                .message(message)
                .build();
    }

    public static ErrorResponse buildInvalidDateFormatError(String date) {
        String message = format("Invalid requested date format [%s], it should be in this format [dd.mm.yyyy]", date);
        log.warn(message);

        return ErrorResponse.builder()
                .errorCode(INVALID_REQUEST_BODY)
                .message(message)
                .build();
    }

    public static ErrorResponse buildInvalidDateError(String date) {
        String message = format("Invalid date [%s], it should be before or today", date);
        log.warn(message);

        return ErrorResponse.builder()
                .errorCode(INVALID_REQUEST_BODY)
                .message(message)
                .build();
    }

    public static ErrorResponse buildCurrencyOrDateNotFound(String currency, String date) {
        String message = format("There is no currency such [%s] or date [%s] not exists", currency, date);
        log.warn(message);

        return ErrorResponse.builder()
                .errorCode(ENTITY_NOT_FOUND)
                .message(message)
                .build();
    }

    public static ErrorResponse buildCurrencyNotFoundByDate(String date) {
        String message = format("There is no currencies by date [%s]", date);
        log.warn(message);

        return ErrorResponse.builder()
                .errorCode(ENTITY_NOT_FOUND)
                .message(message)
                .build();
    }

    public static ErrorResponse buildInvalidAmountError(String amount) {
        String message = format("Amount [%s] should be greater than 0", amount);
        log.warn(message);

        return ErrorResponse.builder()
                .errorCode(INVALID_AMOUNT)
                .message(message)
                .build();
    }

    public static ErrorResponse buildInvalidAmountError() {
        String message = "Amount should be numeric value";
        log.warn(message);

        return ErrorResponse.builder()
                .errorCode(INVALID_AMOUNT)
                .message(message)
                .build();
    }
}
