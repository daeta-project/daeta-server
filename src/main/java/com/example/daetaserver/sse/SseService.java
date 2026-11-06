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
        emitter.onCompletion(() -> emitterRepository.saveChat(userId));

        // 타임아웃 발생 시
        emitter.onTimeout(() -> {
            emitter.complete();
            emitterRepository.delete(userId);
        });

        // 연결 오류 발생 시
        emitter.onError(e ->
                emitterRepository.delete(userId)
        );

        // 최초 연결 이벤트 전송
        sendEvent(userId, "connect", "SSE 연결 성공");

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
            emitterRepository.appendSendContent(userId,data); //보내둔 내용 임시 저장

        } catch (IOException e) {
            emitterRepository.delete(userId);
            emitter.completeWithError(e);
        }
    }

    // 3. 연결 종료
    public void disconnect(String userId) {

        SseEmitter emitter = emitterRepository.findByUserId(userId);

        if (emitter != null) {
            emitter.complete();
            emitterRepository.delete(userId);
        }
    }
}