package com.momentree.domain.post.post.dto.response;

import com.momentree.domain.post.post.entity.Post;

public record PostResponseDto(
    String content
) {
    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getContent()
        );
    }
}
