package com.example.daetaserver.chatbot;

import com.example.daetaserver.global.common.IdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Chatbot extends IdEntity {
    //클라이언트에서 필요한 정보
    @Column(length = 10, nullable = false)
    private String name;
    private String profileURL;
    @Column(nullable = false)
    private String information;
    //ai에서 추가적으로 필요한 정보
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String MBTI;
    @Column(nullable = false)
    private String prompt;
}
