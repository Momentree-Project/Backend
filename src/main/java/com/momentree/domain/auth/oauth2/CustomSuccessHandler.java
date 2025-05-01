package com.momentree.domain.auth.oauth2;

import com.momentree.domain.auth.jwt.AccessTokenProvider;
import com.momentree.domain.auth.jwt.RefreshTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 발급
        String accessToken = accessTokenProvider.generateAccessToken(username, role);
        String refreshToken = refreshTokenProvider.createAndStoreRefreshToken(customUserDetails.getUserId());

        // 리프레시 토큰을 쿠키로 설정
        refreshTokenProvider.setRefreshTokenCookie(response, refreshToken);

        // 액세스 토큰을 바디로 설정
        accessTokenProvider.setAccessTokenBody(response, accessToken, customUserDetails);

        log.info("accessToken={}", accessToken);
        log.info("refreshToken={}", refreshToken);
    }

}
