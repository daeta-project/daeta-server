package com.example.daetaserver.chatbot.presentation;

import com.example.daetaserver.chatbot.ChatbotService;
import com.example.daetaserver.chatbot.presentation.dto.ChatbotProfileDTO;
import com.example.daetaserver.global.common.ResponseJson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    //chatbot 프로필 상세조회 api
    @GetMapping("/{chatbot-id}")
    public ResponseJson<ChatbotProfileDTO> getChatbotProfile(
            @PathVariable(name = "chatbot-id") String chatbotId
    ){
        return ResponseJson.success(chatbotService.getChatbotProfile(chatbotId));
    }
}
