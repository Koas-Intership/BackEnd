package com.example.koas.global.aop;



import com.example.koas.global.exception.ErrorCode;
import com.example.koas.global.exception.GlobalException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThrowIfEmpty {
    Class<? extends RuntimeException> exception() default GlobalException.class;
    ErrorCode errorCode() default ErrorCode.DATA_NOT_FOUND;
}
