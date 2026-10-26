package com.example.daetaserver.chat.presentation.dto;

import com.example.daetaserver.chat.Chat;
import com.example.daetaserver.chat.Sender;

import java.time.LocalDateTime;

public record ReadChatDTO(
        String content,
        Sender sender,
        LocalDateTime createAt
) {
    public ReadChatDTO EntityToDTO(Chat chat){
        return new ReadChatDTO(
                chat.getContent(),
                chat.getSender(),
                chat.getCreatAt()
        );
    }
}
