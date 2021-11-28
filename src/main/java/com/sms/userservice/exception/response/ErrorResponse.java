package com.sms.userservice.exception.response;

import com.sms.userservice.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Integer statusCode;
    private ErrorCode errorCode;
    private String message;
}
