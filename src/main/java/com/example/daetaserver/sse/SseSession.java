package com.example.daetaserver.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public record SseSession(
        SseEmitter emitter,
        StringBuilder content,
        String chatroomId
){

}
