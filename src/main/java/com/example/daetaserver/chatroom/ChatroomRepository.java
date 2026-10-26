package com.example.daetaserver.chatroom;

import com.example.daetaserver.chatroom.presentation.dto.ReadChatroomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, String> {
    boolean existsByUser_IdAndChatbot_Id(String userId, String chatbotId);

    @Query("SELECT r.id AS id, c.name AS name, c.profileURL AS profileURL " +
            "FROM Chatroom r " +
            "INNER JOIN r.chatbot c " +
            "WHERE r.user.id = :userId")
    List<ReadChatroomDTO> findAllByUser_Id(String userId);
}