package com.momentree.domain.category.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.category.dto.request.PatchScheduleCategoryRequestDto;
import com.momentree.domain.category.dto.request.PostScheduleCategoryRequestDto;
import com.momentree.domain.category.dto.response.ScheduleCategoryResponseDto;

import java.util.List;

public interface CategoryService {
    ScheduleCategoryResponseDto postScheduleCategory(CustomOAuth2User loginUser, PostScheduleCategoryRequestDto requestDto);
    List<ScheduleCategoryResponseDto> getScheduleCategory(CustomOAuth2User loginUser);
    ScheduleCategoryResponseDto patchScheduleCategory(CustomOAuth2User loginUser, PatchScheduleCategoryRequestDto requestDto);
    void deleteScheduleCategory(CustomOAuth2User loginUser, Long categoryId);
}
