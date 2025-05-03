package com.momentree.domain.auth.service.impl;

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
    public String refreshAccessToken(String refreshToken) {
        User user = refreshTokenProvider.validateRefreshToken(refreshToken);
        if (user == null) {
            throw new BaseException(ErrorCode.INVALID_JWT);
        }
        return accessTokenProvider.generateAccessToken(user.getId(), user.getUsername(), String.valueOf(user.getRole()));
    }
}
