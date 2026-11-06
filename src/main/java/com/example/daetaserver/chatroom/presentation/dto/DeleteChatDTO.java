package com.example.daetaserver.chatroom.presentation.dto;

import java.time.LocalDateTime;

public record DeleteChatDTO(
        LocalDateTime startAt,
        LocalDateTime endAt
) {
}
