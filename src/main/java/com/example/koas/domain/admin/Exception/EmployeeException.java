package com.example.koas.domain.admin.Exception;

import com.example.koas.global.exception.ErrorCode;
import com.example.koas.global.exception.GlobalException;

public class EmployeeException extends GlobalException
{
    public EmployeeException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public EmployeeException(ErrorCode errorCode) {
        super(errorCode, null);
    }
}
