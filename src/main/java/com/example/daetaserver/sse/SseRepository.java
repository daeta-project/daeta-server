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

    private final Map<String, SseEmitter> emitters =
            new ConcurrentHashMap<>();
    private final Map<String, StringBuilder> sendContents =
            new ConcurrentHashMap<>();
    private final Map<String, String> chatroomIds =
            new ConcurrentHashMap<>();

    public void save(String userId, SseEmitter emitter, String chatroomId) {
        emitters.put(userId, emitter);
        sendContents.put(userId, new StringBuilder());
        chatroomIds.put(userId, chatroomId);
    }

    public SseEmitter findByUserId(String userId) {
        return emitters.get(userId);
    }

    public void appendSendContent(String userId, Object data){
        StringBuilder content = sendContents.get(userId);

        if (content != null) {
            content.append(data);
        }
    }

    public void delete(
            String userId,
            SseEmitter emitter
    ) {
        if (emitters.remove(userId, emitter)) {
            sendContents.remove(userId);
            chatroomIds.remove(userId);
        }
    }

    public void saveChat(String userId){
        chatroomService.saveChat(
                sendContents.get(userId).toString(),
                chatroomIds.get(userId)
        );
    }
}