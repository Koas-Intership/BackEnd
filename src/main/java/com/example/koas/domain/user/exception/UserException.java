package com.example.koas.domain.user.exception;


import com.example.koas.global.exception.ErrorCode;
import com.example.koas.global.exception.GlobalException;

public class UserException extends GlobalException
{
    public UserException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UserException(ErrorCode errorCode) {
        super(errorCode, null);
    }
}
