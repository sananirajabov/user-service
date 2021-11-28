package com.sms.userservice.exception;

import com.sms.userservice.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public interface HttpHandledException {

    ErrorResponse getErrorResponse();

    HttpStatus getHttpStatus();
}
