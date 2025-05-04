package com.momentree.domain.auth.service;

import com.momentree.domain.auth.dto.response.AccessTokenWithUserResponseDto;

public interface AuthService {
    AccessTokenWithUserResponseDto refreshAccessToken(String refreshToken);
}
