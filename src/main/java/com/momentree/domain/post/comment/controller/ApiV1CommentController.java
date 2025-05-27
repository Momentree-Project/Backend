package com.momentree.domain.post.comment.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.comment.dto.request.PostCommentRequest;
import com.momentree.domain.post.comment.dto.response.PostCommentResponse;
import com.momentree.domain.post.comment.service.CommentService;
import com.momentree.global.exception.BaseResponse;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("{post-id}")
    public BaseResponse<List<PostCommentResponse>> getAllComments (
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PathVariable("post-id") Long postId
    ) {
        return new BaseResponse<>(commentService.getAllComments(loginUser, postId));
    }

    @DeleteMapping("{comment-id}")
    public BaseResponse<Void> deleteComment(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PathVariable("comment-id") Long commentId
    ) {
        commentService.deleteComment(loginUser, commentId);
        return new BaseResponse<>(ErrorCode.SUCCESS);
    }
}
