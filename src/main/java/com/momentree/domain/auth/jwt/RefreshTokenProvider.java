package com.momentree.domain.auth.jwt;

import com.momentree.domain.auth.entity.AuthToken;
import com.momentree.domain.auth.repository.AuthTokenRepository;
import com.momentree.domain.user.entity.User;
import com.momentree.domain.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenProvider {

    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;
    private final TokenProperty tokenProperty;

    @Transactional
    public String createAndStoreRefreshToken(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        String refreshToken = RefreshTokenGenerator.generateRefreshToken();

        // 기존 토큰이 있으면 업데이트, 없으면 새로 생성
        AuthToken authToken = authTokenRepository.findAuthTokenByUser(user)
                .map(existingToken -> {
                    existingToken.updateAuthToken(refreshToken, LocalDateTime.now().plusHours(tokenProperty.getRefreshTokenExpiration()));
                    return existingToken;
                }).orElseGet(() -> AuthToken.builder()
                        .refreshToken(refreshToken)
                        .expiresAt(LocalDateTime.now().plusHours(tokenProperty.getRefreshTokenExpiration()))
                        .user(user)
                        .build());

        authTokenRepository.save(authToken);

        return refreshToken;
    }

    public User validateRefreshToken(String refreshToken) {
        AuthToken authToken = authTokenRepository.findByTokenWithUser(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (LocalDateTime.now().isBefore(authToken.getExpiresAt())) {
            return authToken.getUser();
        }
        return null;
    }

    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) (tokenProperty.getRefreshTokenExpiration() * 60 * 60);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(cookieMaxAge);

        response.addCookie(refreshTokenCookie);
    }

}
