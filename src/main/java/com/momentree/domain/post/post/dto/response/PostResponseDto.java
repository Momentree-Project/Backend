package com.momentree.domain.post.post.dto.response;

import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.dto.response.UserInfoResponseDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record PostResponseDto(
    Long postId,
    String content,
    UserInfoResponseDto userId,
    Long loginUserId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<String> imageUrls,
    List<Long> imageIds
) {
    public static PostResponseDto of(Post post, Long loginUserId) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                UserInfoResponseDto.from(post.getUser()),
                loginUserId,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }
    public static PostResponseDto of(Post post, Long loginUserId, List<String> imageUrls, List<Long> imageIds) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                UserInfoResponseDto.from(post.getUser()),
                loginUserId,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                imageUrls != null ? imageUrls : Collections.emptyList(),
                imageIds != null ? imageIds : Collections.emptyList()
        );
    }
}
