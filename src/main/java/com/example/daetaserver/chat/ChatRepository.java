package com.example.daetaserver.chat;

import com.example.daetaserver.chatroom.presentation.dto.ReadChatDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {
    List<ReadChatDTO> findAllByChatroom_IdAndCreatAtAfter(String chatroomId, LocalDateTime creatAtAfter);
}
