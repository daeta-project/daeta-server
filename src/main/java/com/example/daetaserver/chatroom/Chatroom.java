package com.example.daetaserver.chatroom;

import com.example.daetaserver.chatbot.Chatbot;
import com.example.daetaserver.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints ={
                //유저와 챗봇은 서로 하나의 채팅방만 생성 가능함.
                @UniqueConstraint(
                        name = "uk_chatroom_user_chatbot",
                        columnNames = {"user_id", "chatbot_id"}
                )
        }
)
public class Chatroom{
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatbot_id")
    private Chatbot chatbot;
}
