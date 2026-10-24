package com.example.daetaserver.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, String> {
    boolean existsByUser_IdAndChatbot_Id(String userId, String chatbotId);

    List<Chatroom> findAllByUser_Id(String userId);
}
