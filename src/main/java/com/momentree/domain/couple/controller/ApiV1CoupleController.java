package com.momentree.domain.couple.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.couple.dto.request.CoupleConnectRequestDto;
import com.momentree.domain.couple.dto.response.CoupleConnectResponseDto;
import com.momentree.domain.couple.dto.response.CoupleDisconnectResponseDto;
import com.momentree.domain.couple.service.CoupleService;
import com.momentree.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couples")
public class ApiV1CoupleController {

    private final CoupleService coupleService;

    @PostMapping
    public BaseResponse<CoupleConnectResponseDto> connectCouple(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody CoupleConnectRequestDto requestDto) {
        Long userId = loginUser.getUserId();
        CoupleConnectResponseDto connectResponse = coupleService.connectCouple(userId, requestDto);
        return new BaseResponse<>(connectResponse);
    }

    @DeleteMapping("/{coupleId}")
    public BaseResponse<CoupleDisconnectResponseDto> disconnectCouple(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PathVariable Long coupleId) {
        CoupleDisconnectResponseDto response = coupleService.disconnectCouple(loginUser.getUserId(), coupleId);
        return new BaseResponse<>(response);
    }

}
