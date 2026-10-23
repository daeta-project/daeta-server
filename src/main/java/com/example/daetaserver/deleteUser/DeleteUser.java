package com.example.daetaserver.deleteUser;

import com.example.daetaserver.user.User;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
public class DeleteUser {
    @Id
    String id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @CreatedDate
    LocalDateTime deleteAt;
}
