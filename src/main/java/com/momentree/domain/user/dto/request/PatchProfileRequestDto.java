package com.momentree.domain.user.dto.request;


public record PatchProfileRequestDto(
        String username,
        String statusMessage
) {
}
