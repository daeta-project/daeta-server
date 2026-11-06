package com.example.daetaserver.chat;

import com.example.daetaserver.chat.presentation.dto.ReadChatDTO;
import com.example.daetaserver.chatroom.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {
    List<ReadChatDTO> findAllByChatroom_IdAndCreatAtAfter(String chatroomId, LocalDateTime creatAtAfter);
    List<ReadChatDTO> findAllByChatroom_Id(String chatroomId);

    int deleteAllByChatroom_IdAndCreatAtBetween(String chatroomId, LocalDateTime creatAtAfter, LocalDateTime creatAtBefore);
}
