package com.example.daetaserver.chatroom;

import com.example.daetaserver.chat.Chat;
import com.example.daetaserver.chat.ChatRepository;
import com.example.daetaserver.chat.presentation.dto.ReadChatDTO;
import com.example.daetaserver.chatbot.ChatbotRepository;
import com.example.daetaserver.chatroom.presentation.dto.DeleteChatDTO;
import com.example.daetaserver.global.error.CustomException;
import com.example.daetaserver.global.error.ErrorCode;
import com.example.daetaserver.global.security.JWT.TokenManager;
import com.example.daetaserver.user.User;
import com.example.daetaserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;
    private final ChatbotRepository chatbotRepository;
    private final ChatRepository chatRepository;
    private final TokenManager tokenManager;

    private void isYourChatroom(String chatroomId, String userId){
        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_CHATROOM));
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));
        if(!chatroom.getUser().equals(user)){
            throw new CustomException(ErrorCode.IS_NOT_YOUR_CHATROOM);
        }
    }

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


    public List<ReadChatDTO> getChat(String token, LocalDateTime time, String id){
        isYourChatroom(id, tokenManager.getSubject(token));

        if (time==null){
            return chatRepository.findAllByChatroom_IdAndCreatAtAfter(id, time);
        }
        return chatRepository.findAllByChatroom_Id(id);
    }

    public void deleteChats(DeleteChatDTO dto, String token, String id){
        isYourChatroom(id, tokenManager.getSubject(token));

        chatRepository.deleteAllByChatroom_IdAndCreatAtBetween(id, dto.startAt(), dto.endAt());
    }
}
