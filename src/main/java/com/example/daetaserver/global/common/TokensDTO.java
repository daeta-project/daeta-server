package com.example.daetaserver.global.common;

import lombok.Builder;

@Builder
public record TokensDTO(
        String accessToken,
        String refreshToken
) {
}
