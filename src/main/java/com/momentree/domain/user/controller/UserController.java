package com.momentree.domain.user.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
import com.momentree.domain.user.dto.response.GetProfileResponseDto;
import com.momentree.domain.user.dto.response.UserAdditionalInfoResponseDto;
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
public class UserController {
    private final UserService userService;

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

}
