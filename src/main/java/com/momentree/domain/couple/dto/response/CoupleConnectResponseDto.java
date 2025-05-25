package com.momentree.domain.couple.dto.response;

import java.time.LocalDate;

public record CoupleConnectResponseDto(Long coupleId,
                                       Long userId1,
                                       Long userId2,
                                       LocalDate coupleStartedDay) {
    public static CoupleConnectResponseDto of(Long coupleId,
                                                Long userId1,
                                                Long userId2,
                                                LocalDate coupleStartedDate) {
        return new CoupleConnectResponseDto(
                coupleId,
                userId1,
                userId2,
                coupleStartedDate
        );
    }
}
