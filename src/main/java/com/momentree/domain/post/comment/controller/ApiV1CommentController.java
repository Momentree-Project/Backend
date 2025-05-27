package com.momentree.domain.post.comment.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.comment.dto.request.PostCommentRequest;
import com.momentree.domain.post.comment.dto.response.PostCommentResponse;
import com.momentree.domain.post.comment.service.CommentService;
import com.momentree.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/comments")
public class ApiV1CommentController {
    private final CommentService commentService;

    @PostMapping
    public BaseResponse<PostCommentResponse> postComment(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody PostCommentRequest postCommentRequest
            ) {
        return new BaseResponse<>(commentService.postComment(loginUser, postCommentRequest));
    }
}
