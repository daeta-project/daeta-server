package com.example.daetaserver.chatbot.presentation;

import com.example.daetaserver.chatbot.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;
}
