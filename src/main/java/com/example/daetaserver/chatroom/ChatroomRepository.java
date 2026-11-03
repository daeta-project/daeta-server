package com.example.daetaserver.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, String> {
    boolean existsByUser_IdAndChatbot_Id(String userId, String chatbotId);

}