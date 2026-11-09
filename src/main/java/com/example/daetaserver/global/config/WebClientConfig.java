package com.example.daetaserver.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${custom.baseUrl.aiServer}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl) //ai서버 baseUrl지정
                .defaultHeader(
                        HttpHeaders.ACCEPT,
                        MediaType.TEXT_EVENT_STREAM_VALUE
                )
                .build();
    }
}
