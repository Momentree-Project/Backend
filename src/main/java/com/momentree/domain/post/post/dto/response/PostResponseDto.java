package com.momentree.domain.post.post.dto.response;

import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.dto.response.UserInfoResponseDto;

import java.time.LocalDateTime;

public record PostResponseDto(
    Long postId,
    String content,
    UserInfoResponseDto userId,
    Long loginUserId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String imageUrl
) {
    public static PostResponseDto of(Post post, Long loginUserId) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                UserInfoResponseDto.from(post.getUser()),
                loginUserId,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                null
        );
    }
    public static PostResponseDto of(Post post, Long loginUserId, String imageUrl) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                UserInfoResponseDto.from(post.getUser()),
                loginUserId,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                imageUrl
        );
    }
}
