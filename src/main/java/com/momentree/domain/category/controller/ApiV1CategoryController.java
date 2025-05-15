package com.momentree.domain.category.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.category.dto.request.PatchScheduleCategoryRequestDto;
import com.momentree.domain.category.dto.request.PostScheduleCategoryRequestDto;
import com.momentree.domain.category.dto.response.ScheduleCategoryResponseDto;
import com.momentree.domain.category.service.CategoryService;
import com.momentree.global.exception.BaseResponse;
import com.momentree.global.exception.ErrorCode;
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
    public BaseResponse<ScheduleCategoryResponseDto> postScheduleCategory(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody PostScheduleCategoryRequestDto requestDto
            ) {
        return new BaseResponse<>(categoryService.postScheduleCategory(loginUser, requestDto));
    }

    @GetMapping
    public BaseResponse<List<ScheduleCategoryResponseDto>> getScheduleCategory(
            @AuthenticationPrincipal CustomOAuth2User loginUser
    ) {
        return new BaseResponse<>(categoryService.getScheduleCategory(loginUser));
    }

    @PatchMapping
    public BaseResponse<ScheduleCategoryResponseDto> patchScheduleCategory(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody PatchScheduleCategoryRequestDto requestDto
            ) {
        return new BaseResponse<>(categoryService.patchScheduleCategory(loginUser, requestDto));
    }

    @DeleteMapping("/{category-id}")
    public BaseResponse<Void> deleteScheduleCategory(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PathVariable("category-id") Long categoryId
    ) {
        categoryService.deleteScheduleCategory(loginUser, categoryId);
        return new BaseResponse<>(ErrorCode.SUCCESS);
    }
}
