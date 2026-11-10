package com.example.daetaserver.chat;

import com.example.daetaserver.chatroom.Chatroom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @Column(nullable = false)
    String content;

    @ManyToOne
    Chatroom chatroom;

    @CreatedDate
    LocalDateTime creatAt;

    @Enumerated(value = EnumType.STRING)
    Sender sender;
}
