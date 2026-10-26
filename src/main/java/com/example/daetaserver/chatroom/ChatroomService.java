package com.example.daetaserver.chatroom;

import com.example.daetaserver.chatbot.ChatbotRepository;
import com.example.daetaserver.global.error.CustomException;
import com.example.daetaserver.global.error.ErrorCode;
import com.example.daetaserver.global.security.JWT.TokenManager;
import com.example.daetaserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;
    private final ChatbotRepository chatbotRepository;
    private final TokenManager tokenManager;

    public void createNewChatroom(String token, String chatbotId){
        final String userId = tokenManager.getSubject(token);

        if (chatroomRepository.existsByUser_IdAndChatbot_Id(userId,chatbotId)){
            throw new CustomException(ErrorCode.EXIST_CHATROOM);
        }

        Chatroom chatroom = Chatroom.builder()
                .user(userRepository.findById(userId).orElseThrow())
                .chatbot(chatbotRepository.findById(chatbotId).orElseThrow())
                .build();
        chatroomRepository.save(chatroom);
    }

}
