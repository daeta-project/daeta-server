package com.example.daetaserver.deleteUser;

import com.example.daetaserver.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteUser {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @CreatedDate
    LocalDateTime deleteAt;
}
