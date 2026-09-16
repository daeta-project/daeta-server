package com.example.daetaserver.global.error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
}
