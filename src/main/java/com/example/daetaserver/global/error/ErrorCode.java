package com.example.daetaserver.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당하는 유저를 찾을 수 없습니다"),

    NOT_FOUND_CHATBOT(HttpStatus.NOT_FOUND, "해당하는 챗봇을 찾을 수 없습니다"),

    NOT_FOUND_WAY(HttpStatus.INTERNAL_SERVER_ERROR, "해당 방법을 찾을 수 없음"),

    LOGIN_FALSE(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 틀렸습니다");

    private final HttpStatus status;
    private final String message;
}
