package com.example.koas.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode
{
    DATA_NOT_FOUND("DATA_NOT_FOUND", "데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("INVALID_PASSWORD", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    TIME_CONFLICT("RESERVATION_CONFLICT", "이미 예약된 시간과 겹칩니다.",HttpStatus.CONFLICT);
    private final String code;
    private final String message;
    private final HttpStatus status;
}
