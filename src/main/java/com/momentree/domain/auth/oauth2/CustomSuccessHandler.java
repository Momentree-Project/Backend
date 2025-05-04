package com.momentree.domain.auth.oauth2;

import com.momentree.domain.auth.jwt.RefreshTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final RefreshTokenProvider refreshTokenProvider;

    @Value("${custom.dev.frontUrl}")
    private String frontUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        // 토큰 발급
        String refreshToken = refreshTokenProvider.createAndStoreRefreshToken(customUserDetails.getUserId());

        // 리프레시 토큰을 쿠키로 설정
        refreshTokenProvider.setRefreshTokenCookie(response, refreshToken);

        response.sendRedirect(frontUrl + "/login/oauth2/success");

        log.info("refreshToken={}", refreshToken);
    }

}
