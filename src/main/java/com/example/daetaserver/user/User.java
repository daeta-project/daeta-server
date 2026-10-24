package com.example.daetaserver.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User {
    
    //기본키
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    
    //사용자 이름
    @Getter
    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, unique = true)
    private String email; // 구글 계정 식별용 이메일

    @Getter
    private String profileUrl;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate; // 생성 날짜
}
