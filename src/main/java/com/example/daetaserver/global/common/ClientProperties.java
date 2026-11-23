package com.example.daetaserver.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientProperties{
    SSE_URL("api/ai/chat"),
    RECOMMEND_URL("api/ai/recommend");

    private final String url;
}
