package com.momentree.domain.user.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
import com.momentree.domain.user.dto.response.UserAdditionalInfoResponseDto;
import com.momentree.domain.user.service.UserService;
import com.momentree.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/additional-info")
    public BaseResponse<UserAdditionalInfoResponseDto> patchUserAdditionalInfo(
            @AuthenticationPrincipal CustomOAuth2User userDetails,
            @RequestBody UserAdditionalInfoRequestDto requestDto) {
        Long userId = userDetails.getUserId();
        UserAdditionalInfoResponseDto responseDto = userService.patchUserAdditionalInfo(userId, requestDto);
        return new BaseResponse<>(responseDto);
    }

}
