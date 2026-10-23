package com.example.daetaserver.user;

import com.example.daetaserver.global.common.IdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@SuperBuilder
@Table(name = "users")
public class User extends IdEntity {
    //사용자 이름
    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, unique = true)
    private String email; // 구글 계정 식별용 이메일

    private String profileUrl;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate; // 생성 날짜
}
