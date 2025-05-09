package com.momentree.domain.user.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.couple.dto.request.PatchCoupleStartedDayRequestDto;
import com.momentree.domain.couple.dto.response.PatchCoupleStartedDayResponseDto;
import com.momentree.domain.couple.service.CoupleService;
import com.momentree.domain.user.dto.request.PatchMarketingConsentRequestDto;
import com.momentree.domain.user.dto.request.PatchProfileRequestDto;
import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
import com.momentree.domain.user.dto.response.*;
import com.momentree.domain.user.service.UserService;
import com.momentree.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/me")
public class ApiV1UserController {
    private final UserService userService;
    private final CoupleService coupleService;

    // 회원가입 시 추가정보 등록 (최초 1회)
    @PatchMapping("/additional-info")
    public BaseResponse<UserAdditionalInfoResponseDto> patchUserAdditionalInfo(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody UserAdditionalInfoRequestDto requestDto) {
        Long userId = loginUser.getUserId();
        UserAdditionalInfoResponseDto responseDto = userService.patchUserAdditionalInfo(userId, requestDto);
        return new BaseResponse<>(responseDto);
    }

    @GetMapping
    public BaseResponse<GetProfileResponseDto> getMyProfile(@AuthenticationPrincipal CustomOAuth2User loginUser) {
        return new BaseResponse<>(userService.getMyProfile(loginUser.getUserId()));
    }

    @PatchMapping("/profile")
    public BaseResponse<PatchProfileResponseDto> patchMyProfile(@AuthenticationPrincipal CustomOAuth2User loginUser,
                                                                @RequestBody PatchProfileRequestDto patchProfileRequestDto) {
        PatchProfileResponseDto responseDto = userService.patchMyProfile(loginUser.getUserId(), patchProfileRequestDto);
        return new BaseResponse<>(responseDto);
    }

    @PatchMapping("/coupleStartedDay")
    public BaseResponse<PatchCoupleStartedDayResponseDto> patchMyProfile(@AuthenticationPrincipal CustomOAuth2User loginUser,
                                                                         @RequestBody PatchCoupleStartedDayRequestDto requestDto) {
        PatchCoupleStartedDayResponseDto responseDto = coupleService.patchCoupleStartedDay(loginUser.getUserId(), requestDto);
        return new BaseResponse<>(responseDto);
    }

    @PatchMapping("/marketing-consent")
    public BaseResponse<PatchMarketingConsentResponseDto> patchMyProfile(@AuthenticationPrincipal CustomOAuth2User loginUser,
                                                                @RequestBody PatchMarketingConsentRequestDto requestDto) {
        PatchMarketingConsentResponseDto responseDto = userService.patchMarketingConsent(loginUser.getUserId(), requestDto);
        return new BaseResponse<>(responseDto);
    }

    @DeleteMapping
    public BaseResponse<Void> deleteMyProfile(@AuthenticationPrincipal CustomOAuth2User loginUser) {
        return new BaseResponse<>(userService.deleteMyProfile(loginUser.getUserId()));
    }

}
