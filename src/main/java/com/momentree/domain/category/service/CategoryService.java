package com.momentree.domain.category.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.category.request.PostScheduleCategoryRequestDto;
import com.momentree.domain.category.response.PostScheduleCategoryResponseDto;

import java.util.List;

public interface CategoryService {
    PostScheduleCategoryResponseDto postScheduleCategory(CustomOAuth2User loginUser, PostScheduleCategoryRequestDto requestDto);
    List<PostScheduleCategoryResponseDto> getScheduleCategory(CustomOAuth2User loginUser);
}
