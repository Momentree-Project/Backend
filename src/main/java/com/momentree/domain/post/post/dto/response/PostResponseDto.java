package com.momentree.domain.post.post.dto.response;

import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.dto.response.UserInfoResponseDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record PostResponseDto(
    Long postId,
    String content,
    UserInfoResponseDto userId, // 게시글 작성한 유저의 정보
    Long loginUserId,   // 로그인한 유저의 ID
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long likesCount,
    List<String> imageUrls,
    List<Long> imageIds,
    Long commentCount
) {
    public static PostResponseDto of(
            Post post,
            Long loginUserId,
            List<String> imageUrls,
            List<Long> imageIds,
            String profileImageUrl,
            Long commentCount
    ) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                UserInfoResponseDto.from(post.getUser(), profileImageUrl),
                loginUserId,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getLikeCount(),
                imageUrls != null ? imageUrls : Collections.emptyList(),
                imageIds != null ? imageIds : Collections.emptyList(),
                commentCount
        );
    }
}
