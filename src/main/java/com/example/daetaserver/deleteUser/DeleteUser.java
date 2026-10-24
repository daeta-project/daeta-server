package com.example.daetaserver.deleteUser;

import com.example.daetaserver.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
