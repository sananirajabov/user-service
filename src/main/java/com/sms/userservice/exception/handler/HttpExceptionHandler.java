package com.sms.userservice.exception.handler;

import com.sms.userservice.exception.EntityAlreadyExistsException;
import com.sms.userservice.exception.EntityNotFoundException;
import com.sms.userservice.exception.HttpHandledException;
import com.sms.userservice.exception.ValidationFailedException;
import com.sms.userservice.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

import static com.sms.userservice.exception.code.GeneralErrorCode.*;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class HttpExceptionHandler {

    private static final String NOT_EMPTY_ERROR_CODE = "NotEmpty";

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        log.warn("Entity already exists error occurred", exception);
        return buildHttpErrorResponseEntity(exception);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        log.warn("Entity not found error occurred", exception);
        return buildHttpErrorResponseEntity(exception);
    }

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationFailedException(ValidationFailedException exception) {
        log.warn("Validation failed error occurred", exception);
        return buildHttpErrorResponseEntity(exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception exception) {
        if (exception instanceof HttpMessageNotReadableException) {
            return buildInvalidRequestBodyResponse();
        }

        if (exception instanceof MethodArgumentNotValidException) {
            return buildHttpErrorResponse((MethodArgumentNotValidException) exception);
        }

        return buildGenericInternalServerError();
    }

    private ResponseEntity<ErrorResponse> buildHttpErrorResponse(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        return Optional.of(bindingResult)
                .map(Errors::getFieldError)
                .map(this::generateFieldErrorResponse)
                .orElseGet(this::buildGenericInternalServerError);
    }

    private ResponseEntity<ErrorResponse> generateFieldErrorResponse(FieldError fieldError) {
        String errorCode = fieldError.getCode();
        String errorField = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();
        String errorMessage;

        System.out.println("Code: " + errorCode);

        if (NOT_EMPTY_ERROR_CODE.equalsIgnoreCase(errorCode)) {
            errorMessage = format("[%s] %s", errorField, defaultMessage);
            log.warn(errorMessage);

            return buildHttpErrorResponseEntity(ErrorResponse.builder()
                            .errorCode(MISSING_REQUIRED_REQUEST_FIELD)
                            .message(errorMessage)
                            .build(),
                    BAD_REQUEST);
        }

        return buildGenericInternalServerError();
    }

    private ResponseEntity<ErrorResponse> buildInvalidRequestBodyResponse() {
        String message = "Unable to deserialize request";
        log.warn(message);

        return buildHttpErrorResponseEntity(ErrorResponse.builder()
                        .statusCode(BAD_REQUEST.value())
                        .errorCode(INVALID_REQUIRED_REQUEST_BODY)
                        .message(message)
                        .build(),
                BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> buildGenericInternalServerError() {
        String message = "Internal server error occurred";
        log.warn(message);

        return buildHttpErrorResponseEntity(ErrorResponse.builder()
                        .statusCode(INTERNAL_SERVER_ERROR.value())
                        .errorCode(UNHANDLED_SERVICE_ERROR)
                        .message(message)
                        .build(),
                INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildHttpErrorResponseEntity(HttpHandledException exception) {
        return buildHttpErrorResponseEntity(exception.getErrorResponse(), exception.getHttpStatus());
    }

    private ResponseEntity<ErrorResponse> buildHttpErrorResponseEntity(ErrorResponse errorResponse,
                                                                       HttpStatus httpStatus) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .statusCode(errorResponse.getStatusCode())
                .errorCode(errorResponse.getErrorCode())
                .message(errorResponse.getMessage())
                .build(), httpStatus);
    }

}
