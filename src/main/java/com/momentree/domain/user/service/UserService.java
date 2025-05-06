package com.momentree.domain.user.service;

import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
import com.momentree.domain.user.dto.response.GetProfileResponseDto;
import com.momentree.domain.user.dto.response.UserAdditionalInfoResponseDto;

public interface UserService {
    UserAdditionalInfoResponseDto patchUserAdditionalInfo(Long userId, UserAdditionalInfoRequestDto requestDto);
    GetProfileResponseDto getMyProfile(Long userId);
}
