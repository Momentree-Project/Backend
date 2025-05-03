package com.momentree.domain.couple.dto.response;

import java.time.LocalDate;

public record CoupleConnectResponseDto(Long userId1,
                                       Long userId2,
                                       LocalDate coupleStartedDay) {
    public static CoupleConnectResponseDto from(Long userId1,
                                                Long userId2,
                                                LocalDate coupleStartedDate) {
        return new CoupleConnectResponseDto(
                userId1,
                userId2,
                coupleStartedDate
        );
    }
}
