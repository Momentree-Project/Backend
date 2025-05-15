package com.momentree.domain.post.post.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.post.dto.request.PostRequestDto;
import com.momentree.domain.post.post.dto.response.PostResponseDto;
import com.momentree.domain.post.post.service.PostService;
import com.momentree.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    @PostMapping
    public BaseResponse<PostResponseDto> createPost(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody PostRequestDto requestDto
    ) {
        return new BaseResponse<>(postService.createPost(loginUser, requestDto));
    }
}
