package com.example.daetaserver.sse;

import com.example.daetaserver.chatroom.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SseRepository {

    private final ChatroomService chatroomService;

    private final Map<String, SseSession> sseMap = new ConcurrentHashMap<>();

    public void save(String userId, SseEmitter emitter, String chatroomId) {
        SseSession sseSession = new SseSession(emitter, new StringBuilder(), chatroomId);
        sseMap.put(userId, sseSession);
    }

    public SseEmitter findByUserId(String userId) {
        return sseMap.get(userId).emitter();
    }

    public void appendSendContent(String userId, Object data){
        StringBuilder content = sseMap.get(userId).content();

        if (content != null) {
            content.append(data);
        }
    }

    public void delete(
            String userId,
            SseEmitter emitter
    ) {
        SseSession sseSession = sseMap.get(userId);
        sseMap.remove(userId, sseSession);
    }

    public void saveChat(String userId){
        chatroomService.saveChat(
                sseMap.get(userId).content().toString(),
                sseMap.get(userId).chatroomId()
        );
    }
}