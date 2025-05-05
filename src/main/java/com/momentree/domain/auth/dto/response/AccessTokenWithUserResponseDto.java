package com.momentree.domain.auth.dto.response;

public record AccessTokenWithUserResponseDto(String accessToken,
                                             String username,
                                             String email,
                                             String userCode,
                                             Long coupleId
) {
    public AccessTokenWithUserResponseDto(String accessToken,
                                          String username,
                                          String email,
                                          String userCode,
                                          Long coupleId) {
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.userCode = userCode;
        this.coupleId = coupleId;
    }
}
