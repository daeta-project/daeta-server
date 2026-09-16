package com.example.daetaserver.chatbot.presentation.dto;

import lombok.Builder;

@Builder
public record ChatbotProfileDTO(
        String name,
        String profileURL,
        String information
) {
}
