package com.example.daetaserver.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SseClientService {

    private final WebClient webClient;
    private final SseService sseService;

    public SseEmitter requestSse(
            String userId,
            String chatroomId,
            String message
    ) {

        SseEmitter emitter = sseService.connect(userId, chatroomId);

        webClient.post()
                .uri("/api/ai/stream")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .bodyValue(Map.of(
                        "userId", userId,
                        "message", message
                ))
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(
                        data -> {
                            sseService.sendEvent(userId, "message", data);
                        },
                        error -> {
                            // 통신 오류 발생 시
                            sseService.sendEvent(
                                    userId,
                                    "error",
                                    "AI 서버와 통신 중 오류가 발생했습니다."
                            );
                            sseService.disconnect(userId);
                        },
                        () -> {
                            // 통신 정상 종료 시
                            sseService.saveChat(userId);
                        }
                );
        return emitter;
    }
}
