package com.momentree.domain.user.service;

import com.momentree.domain.user.dto.request.PatchProfileRequestDto;
import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
import com.momentree.domain.user.dto.response.GetProfileResponseDto;
import com.momentree.domain.user.dto.response.PatchProfileResponseDto;
import com.momentree.domain.user.dto.response.UserAdditionalInfoResponseDto;

public interface UserService {
    UserAdditionalInfoResponseDto patchUserAdditionalInfo(Long userId, UserAdditionalInfoRequestDto requestDto);
    GetProfileResponseDto getMyProfile(Long userId);
    PatchProfileResponseDto patchMyProfile(Long userId, PatchProfileRequestDto patchProfileRequestDto);
}
