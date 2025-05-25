package com.momentree.domain.post.like.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.like.dto.response.GetPostLikesResponse;

public interface LikeService {
    void likePost(CustomOAuth2User loginUser, Long postId);
    GetPostLikesResponse getPostLikesCount(CustomOAuth2User loginUser, Long postId);
}
