package com.momentree.domain.post.comment.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.comment.dto.request.PostCommentRequest;
import com.momentree.domain.post.comment.dto.response.PostCommentResponse;

public interface CommentService {
    PostCommentResponse postComment(CustomOAuth2User loginUser, PostCommentRequest postCommentRequest);
}
