package com.momentree.domain.post.comment.dto.request;

public record PostCommentRequest(
    String content,
    Long postId,
    Integer level,
    Long parentId
) {
}
