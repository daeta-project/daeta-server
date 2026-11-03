package com.example.daetaserver.chatbot;

import com.example.daetaserver.chatbot.presentation.dto.ChatbotProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatbotRepository extends JpaRepository<Chatbot, String> {

    @Query(value = "select name as name, profileURL as profileURL, information as information from Chatbot", nativeQuery = true)
    List<ChatbotProfileDTO> getAllChatbots();
}
