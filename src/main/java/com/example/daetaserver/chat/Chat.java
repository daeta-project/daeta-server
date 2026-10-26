package com.example.daetaserver.chat;

import com.example.daetaserver.chatroom.Chatroom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    String id;

    @Getter
    @Column(nullable = false)
    String content;

    @ManyToOne
    Chatroom chatroom;

    @Getter
    @CreatedDate
    LocalDateTime creatAt;

    @Getter
    @Enumerated(value = EnumType.STRING)
    Sender sender;
}
