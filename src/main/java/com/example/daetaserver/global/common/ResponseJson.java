package com.example.daetaserver.global.common;

import com.example.daetaserver.global.error.ErrorCode;
import lombok.Builder;

import java.util.Map;

@Builder
public class ResponseJson <T>{
    T data;
    boolean success;

    public static<T> ResponseJson<T> success(T data){
        return ResponseJson.<T>builder()
                .data(data)
                .success(true)
                .build();
    }
    public static ResponseJson<Map<String,String>> error(ErrorCode errorCode){
        return ResponseJson.<Map<String,?>>builder()
                .data(Map.of("status", errorCode.getStatus().toString(), "message", errorCode.getMessage()))
                .success(false)
                .build();
    }
}
