package com.example.daetaserver.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SseService {

    private final SseRepository emitterRepository;

    // SSE 연결 유지 시간 (1시간)
    private static final Long TIMEOUT = 60 * 60 * 1000L;

    // 1. SSE 연결 생성
    public SseEmitter connect(String userId, String chatroomId) {

        SseEmitter emitter = new SseEmitter(TIMEOUT);

        // 연결 저장
        emitterRepository.save(userId, emitter, chatroomId);

        // 연결 종료 시
        emitter.onCompletion(() ->
                emitterRepository.delete(userId, emitter)
        );

        // 타임아웃 발생 시
        emitter.onTimeout(() -> {
            emitter.complete();
        });

        // 연결 오류 발생 시
        emitter.onError(e ->
                emitterRepository.delete(userId, emitter)
        );

        sendEvent(
                userId,
                "connect",
                "연결 성공"
        );

        return emitter;
    }

    // 2. 특정 사용자에게 이벤트 전송
    public void sendEvent(
            String userId,
            String eventName,
            Object data
    ) {

        SseEmitter emitter = emitterRepository.findByUserId(userId);

        if (emitter == null) {
            return;
        }

        try {
            emitter.send(
                    SseEmitter.event()
                            .name(eventName)
                            .data(data)
            );

            if ("message".equals(eventName)) {
                emitterRepository.appendSendContent(userId, data);
            }

        } catch (IOException e) {
            emitterRepository.delete(userId, emitter);
            emitter.completeWithError(e);
        }
    }

    // 3. 연결 종료
    public void disconnect(String userId) {

        SseEmitter emitter = emitterRepository.findByUserId(userId);

        if (emitter != null) {
            emitter.complete();
            emitterRepository.delete(userId, emitter);
        }
    }

    // 4. 정상적인 AI 응답 완료
    public void saveChat(String userId) {

        try {
            emitterRepository.saveChat(userId);

            sendEvent(
                    userId,
                    "complete",
                    "AI 응답이 완료되었습니다."
            );
        } finally {
            disconnect(userId);
        }
    }
}