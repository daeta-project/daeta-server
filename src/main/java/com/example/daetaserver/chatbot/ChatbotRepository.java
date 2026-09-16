package com.example.daetaserver.chatbot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotRepository extends JpaRepository<Chatbot, String> {
}
