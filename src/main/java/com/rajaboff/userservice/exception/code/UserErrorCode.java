package com.rajaboff.userservice.exception.code;

import com.rajaboff.httphandler.exception.code.ErrorCode;

public enum UserErrorCode implements ErrorCode  {

    EMAIL_IS_NOT_UPDATABLE;

    @Override
    public void getCode() {
    }
}
