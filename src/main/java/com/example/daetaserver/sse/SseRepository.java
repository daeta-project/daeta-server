package com.example.daetaserver.sse;

import com.example.daetaserver.chat.Chat;
import com.example.daetaserver.chat.ChatRepository;
import com.example.daetaserver.chat.Sender;
import com.example.daetaserver.chatroom.Chatroom;
import com.example.daetaserver.chatroom.ChatroomRepository;
import com.example.daetaserver.global.error.CustomException;
import com.example.daetaserver.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SseRepository {

    private final ChatroomRepository chatroomRepository;
    private final ChatRepository chatRepository;

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
        String content = sseMap.get(userId).content().toString();
        String chatroomId = sseMap.get(userId).chatroomId();

        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_CHATROOM));
        chatRepository.save(Chat.builder()
                .content(content)
                .chatroom(chatroom)
                .sender(Sender.CHATBOT)
                .build()
        );
    }
}