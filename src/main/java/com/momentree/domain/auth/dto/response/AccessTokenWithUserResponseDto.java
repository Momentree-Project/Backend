package com.momentree.domain.auth.dto.response;

import com.momentree.domain.user.entity.User;
import com.momentree.global.constant.Status;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AccessTokenWithUserResponseDto(String accessToken,
                                             String username,
                                             String email,
                                             String userCode,
                                             LocalDate birth,
                                             Long coupleId,
                                             Status status,
                                             LocalDate coupleStartedDay
                                             ) {
    public static AccessTokenWithUserResponseDto of(String accessToken, User user) {
        return AccessTokenWithUserResponseDto.builder()
                .accessToken(accessToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .userCode(user.getUserCode())
                .birth(user.getBirth())
                .coupleId(user.getCouple() == null ? null : user.getCouple().getId())
                .status(user.getStatus())
                .coupleStartedDay(user.getCouple() == null ? null : user.getCouple().getCoupleStartedDay())
                .build();
    }
}
