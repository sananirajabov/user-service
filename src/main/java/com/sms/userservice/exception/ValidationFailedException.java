package com.sms.userservice.exception;

import com.sms.userservice.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class ValidationFailedException extends RuntimeException implements HttpHandledException {

    private final ErrorResponse errorResponse;

    public ValidationFailedException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return this.errorResponse;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
