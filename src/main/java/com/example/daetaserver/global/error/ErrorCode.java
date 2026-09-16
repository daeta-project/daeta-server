package com.example.daetaserver.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    LOGIN_FALSE(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 틀렸습니다");

    private final HttpStatus status;
    private final String message;
}
