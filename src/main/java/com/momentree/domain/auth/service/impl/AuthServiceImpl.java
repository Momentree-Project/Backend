package com.momentree.domain.auth.service.impl;

import com.momentree.domain.auth.dto.response.AccessTokenWithUserResponseDto;
import com.momentree.domain.auth.jwt.AccessTokenProvider;
import com.momentree.domain.auth.jwt.RefreshTokenProvider;
import com.momentree.domain.auth.service.AuthService;
import com.momentree.domain.user.entity.User;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenProvider refreshTokenProvider;
    private final AccessTokenProvider accessTokenProvider;

    @Override
    @Transactional
    public AccessTokenWithUserResponseDto refreshAccessToken(String refreshToken) {
        User user = refreshTokenProvider.validateRefreshToken(refreshToken);
        if (user == null) {
            throw new BaseException(ErrorCode.INVALID_JWT);
        }
        String accessToken = accessTokenProvider.generateAccessToken(user.getId(), user.getUsername(), String.valueOf(user.getRole()));
        return new AccessTokenWithUserResponseDto(
                accessToken,
                user.getUsername(),
                user.getEmail(),
                user.getUserCode(),
                user.getCouple() == null ? null : user.getCouple().getId()
        );
    }
}
