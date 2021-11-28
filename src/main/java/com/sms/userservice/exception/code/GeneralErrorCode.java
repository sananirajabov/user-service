package com.sms.userservice.exception.code;

public enum GeneralErrorCode implements ErrorCode {

    ENTITY_NOT_FOUND,
    ENTITY_ALREADY_EXIST,
    INVALID_REQUIRED_REQUEST_BODY,
    EMAIL_IS_NOT_UPDATABLE,
    INVALID_REQUIRED_REQUEST_FIELD,
    MISSING_REQUIRED_REQUEST_FIELD,
    UNHANDLED_SERVICE_ERROR;

    @Override
    public void getCode() {

    }
}
