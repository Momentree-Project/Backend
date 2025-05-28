package com.momentree.domain.post.comment.dto.response;

import com.momentree.domain.post.comment.entity.Comment;

import java.time.LocalDateTime;

public record PostCommentResponse(
        Long commentId,
        String content,
        String author,
        Long authorId,
        Integer level,
        String authorProfileImageUrl,
        LocalDateTime createdAt,
        Long loginUserId,
        Long parentId,
        Long postId
) {
    public static PostCommentResponse of(
            Comment comment,
            Long loginUserId,
            String profileImageUrl
    ) {
        return new PostCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getUser().getId(),
                comment.getLevel(),
                profileImageUrl,
                comment.getCreatedAt(),
                loginUserId,
                comment.getParent() != null ? comment.getParent().getId() : null,
                comment.getPost().getId()
        );
    }
}