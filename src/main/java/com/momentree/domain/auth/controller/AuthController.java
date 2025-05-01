package com.momentree.domain.auth.controller;

import com.momentree.domain.auth.response.AccessTokenResponseDto;
import com.momentree.domain.auth.service.AuthService;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.BaseResponse;
import com.momentree.global.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/refresh-token")
    public BaseResponse<?> refreshAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new BaseException(ErrorCode.INVALID_JWT);
        }

        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new BaseException(ErrorCode.INVALID_JWT);
        }

        // Access Token 발급
        String refreshedAccessToken = authService.refreshAccessToken(refreshToken);

        return new BaseResponse<>(new AccessTokenResponseDto(refreshedAccessToken));
    }
}
