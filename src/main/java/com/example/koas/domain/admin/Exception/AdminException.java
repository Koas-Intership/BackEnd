package com.example.koas.domain.admin.Exception;


import com.example.koas.global.exception.ErrorCode;
import com.example.koas.global.exception.GlobalException;

public class AdminException extends GlobalException
{
    public AdminException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AdminException(ErrorCode errorCode) {
        super(errorCode, null);
    }
}
