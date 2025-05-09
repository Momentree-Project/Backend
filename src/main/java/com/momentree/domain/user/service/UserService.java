package com.momentree.domain.user.service;

import com.momentree.domain.user.dto.request.PatchMarketingConsentRequestDto;
import com.momentree.domain.user.dto.request.PatchPersonalRequestDto;
import com.momentree.domain.user.dto.request.PatchProfileRequestDto;
import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
import com.momentree.domain.user.dto.response.*;

public interface UserService {
    UserAdditionalInfoResponseDto patchUserAdditionalInfo(Long userId, UserAdditionalInfoRequestDto requestDto);
    GetProfileResponseDto getMyProfile(Long userId);
    PatchProfileResponseDto patchMyProfile(Long userId, PatchProfileRequestDto requestDto);
    PatchPersonalResponseDto patchMyPersonal(Long userId, PatchPersonalRequestDto requestDto);
    PatchMarketingConsentResponseDto patchMyMarketingConsent(Long userId, PatchMarketingConsentRequestDto requestDto);
    Void deleteMyProfile(Long userId);
}
