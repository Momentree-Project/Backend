package com.momentree.domain.category.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.category.request.PostScheduleCategoryRequestDto;
import com.momentree.domain.category.response.PostScheduleCategoryResponseDto;
import com.momentree.domain.category.service.CategoryService;
import com.momentree.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class ApiV1CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public BaseResponse<PostScheduleCategoryResponseDto> postScheduleCategory(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody PostScheduleCategoryRequestDto requestDto
            ) {
        return new BaseResponse<>(categoryService.postScheduleCategory(loginUser, requestDto));
    }

    @GetMapping
    public BaseResponse<List<PostScheduleCategoryResponseDto>> getScheduleCategory(
            @AuthenticationPrincipal CustomOAuth2User loginUser
    ) {
        return new BaseResponse<>(categoryService.getScheduleCategory(loginUser));
    }
}
