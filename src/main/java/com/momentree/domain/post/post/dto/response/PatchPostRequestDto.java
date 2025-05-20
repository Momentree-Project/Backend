package com.momentree.domain.post.post.dto.response;

public record PatchPostRequestDto(
        Long postId,
        String content
) {
}
