package com.example.daetaserver.chatbot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Chatbot{
    //클라이언트에서 필요한 정보
    //기본키
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    //챗봇 이름
    @Column(length = 10, nullable = false)
    private String name;
    //프로필 사진 url
    private String profileURL;
    //챗봇 설명
    @Column(nullable = false)
    private String information;

    //ai에서 추가적으로 필요한 정보
    //담당 과목
    @Column(nullable = false)
    private String subject;
    //성격
    @Column(nullable = false)
    private String MBTI;
    //기본 프롬프트
    @Column(nullable = false)
    private String prompt;
}
