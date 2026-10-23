package com.example.daetaserver.user.presentation.dto;

import lombok.Builder;

@Builder
public record ReadUserDTO(
        String name,
        String profileUrl
) {
}
