package com.example.daetaserver.chat.presentation.dto;

import com.example.daetaserver.chat.Chat;
import com.example.daetaserver.chat.Sender;

import java.time.LocalDateTime;

public record ReadChat(
        String content,
        Sender sender,
        LocalDateTime createAt
) {
    public ReadChat EntityToDTO(Chat chat){
        return new ReadChat(
                chat.getContent(),
                chat.getSender(),
                chat.getCreatAt()
        );
    }
}
