package com.rajaboff.userservice.util;

import com.rajaboff.httphandler.exception.response.ErrorResponse;
import com.rajaboff.userservice.exception.code.UserErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.rajaboff.httphandler.exception.code.GeneralErrorCode.ENTITY_ALREADY_EXIST;
import static com.rajaboff.httphandler.exception.code.GeneralErrorCode.ENTITY_NOT_FOUND;
import static com.rajaboff.userservice.exception.code.UserErrorCode.EMAIL_IS_NOT_UPDATABLE;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class ErrorResponseFactory {

    private ErrorResponseFactory() {
    }

    public static ErrorResponse buildEmailAlreadyExistsError(String email) {
        String message = format("Email already exists [%s]", email);
        log.warn(message);

        return ErrorResponse.builder()
                .statusCode(BAD_REQUEST.value())
                .errorCode(ENTITY_ALREADY_EXIST)
                .message(message)
                .build();
    }

    public static ErrorResponse buildEmailIsNotUpdatable(String email) {
        String message = format("Email is not updatable [%s]", email);
        log.warn(message);

        return ErrorResponse.builder()
                .statusCode(BAD_REQUEST.value())
                .errorCode(EMAIL_IS_NOT_UPDATABLE)
                .message(message)
                .build();
    }

    public static ErrorResponse buildEntityNotFound(UUID userId) {
        String message = format("Entity not found with given id [%s]", userId);
        log.warn(message);

        return ErrorResponse.builder()
                .statusCode(NOT_FOUND.value())
                .errorCode(ENTITY_NOT_FOUND)
                .message(message)
                .build();
    }

}
