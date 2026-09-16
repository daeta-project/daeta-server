package com.example.daetaserver.chatbot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, timeout = 60)
public class ChatbotService {
    private final ChatbotRepository chatbotRepository;
}
