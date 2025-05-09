package com.momentree.domain.couple.dto.response;

public record CoupleDisconnectResponseDto(String userCode) {
    public static CoupleDisconnectResponseDto from(String userCode) {
        return new CoupleDisconnectResponseDto(userCode);
    }
}
