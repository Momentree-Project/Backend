package com.momentree.domain.auth.dto.response;

import java.time.LocalDate;

public record AccessTokenWithUserResponseDto(String accessToken,
                                             String username,
                                             String email,
                                             String userCode,
                                             LocalDate birth,
                                             Long coupleId
) {
    public AccessTokenWithUserResponseDto(String accessToken,
                                          String username,
                                          String email,
                                          String userCode,
                                          LocalDate birth,
                                          Long coupleId) {
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.userCode = userCode;
        this.birth = birth;
        this.coupleId = coupleId;
    }
}
