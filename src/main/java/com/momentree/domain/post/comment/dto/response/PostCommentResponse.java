package com.momentree.domain.post.comment.dto.response;

import com.momentree.domain.post.comment.entity.Comment;

public record PostCommentResponse(
    String content,
    String author,
    Integer level,
    Long loginUserId
) {
    public static PostCommentResponse of(
            Comment comment,
            Long loginUserId
    ) {
        return new PostCommentResponse(
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getLevel(),
                loginUserId
        );
    }
}
