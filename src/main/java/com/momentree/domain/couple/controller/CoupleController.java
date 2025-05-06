package com.momentree.domain.couple.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.couple.dto.request.CoupleConnectRequestDto;
import com.momentree.domain.couple.dto.response.CoupleConnectResponseDto;
import com.momentree.domain.couple.service.CoupleService;
import com.momentree.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couples")
public class CoupleController {

    private final CoupleService coupleService;

    @PostMapping
    public BaseResponse<CoupleConnectResponseDto> connectCouple(
            @AuthenticationPrincipal CustomOAuth2User userDetails,
            @RequestBody CoupleConnectRequestDto requestDto){
        Long userId = userDetails.getUserId();
        CoupleConnectResponseDto connectResponse = coupleService.connectCouple(userId, requestDto);
        return new BaseResponse<>(connectResponse);
    }

}
