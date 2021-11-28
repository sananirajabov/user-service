package com.sms.userservice.exception;

import com.sms.userservice.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException implements HttpHandledException {

    private final ErrorResponse errorResponse;

    public EntityNotFoundException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return this.errorResponse;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
