package com.momentree.domain.post.post.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.post.dto.request.PostRequestDto;
import com.momentree.domain.post.post.dto.response.PatchPostRequestDto;
import com.momentree.domain.post.post.dto.response.PostResponseDto;
import com.momentree.domain.post.post.service.PostService;
import com.momentree.global.exception.BaseResponse;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    @PostMapping
    public BaseResponse<PostResponseDto> createPost(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @ModelAttribute PostRequestDto requestDto
    ) {
        return new BaseResponse<>(postService.createPost(loginUser, requestDto));
    }

    @GetMapping
    public BaseResponse<List<PostResponseDto>> getAllPosts(
            @AuthenticationPrincipal CustomOAuth2User loginUser
    ) {
        return new BaseResponse<>(postService.getAllPosts(loginUser));
    }

    @PatchMapping
    public BaseResponse<PostResponseDto> patchPost(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody PatchPostRequestDto requestDto
    ) {
        return new BaseResponse<>(postService.patchPost(loginUser, requestDto));
    }

    @DeleteMapping("/{post-id}")
    public BaseResponse<Void> deletePost(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PathVariable("post-id") Long postId
    ) {
        postService.deletePost(loginUser, postId);
        return new BaseResponse<>(ErrorCode.SUCCESS);
    }
}
