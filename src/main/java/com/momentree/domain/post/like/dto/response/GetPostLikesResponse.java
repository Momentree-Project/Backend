package com.momentree.domain.post.like.dto.response;

import com.momentree.domain.post.post.entity.Post;

public record GetPostLikesResponse(
        Long postId,
        Boolean isLikedByCurrentUser,
        Long likesCount
) {
    public static GetPostLikesResponse of(Post post, Boolean isLiked) {
        return new GetPostLikesResponse(
                post.getId(),
                isLiked,
                post.getLikeCount()
        );
    }
}
