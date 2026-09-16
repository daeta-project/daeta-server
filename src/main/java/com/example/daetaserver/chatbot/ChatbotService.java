package com.example.daetaserver.chatbot;

import com.example.daetaserver.chatbot.presentation.dto.ChatbotProfileDTO;
import com.example.daetaserver.global.error.CustomException;
import com.example.daetaserver.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, timeout = 60)
public class ChatbotService {
    private final ChatbotRepository chatbotRepository;

    public ChatbotProfileDTO getChatbotProfile(String chatbotId){
        Chatbot chatbot = chatbotRepository.findById(chatbotId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_CHATBOT));
        return ChatbotProfileDTO.builder()
                .name(chatbot.getName())
                .profileURL(chatbot.getProfileURL())
                .information(chatbot.getInformation())
                .build();
    }
}
