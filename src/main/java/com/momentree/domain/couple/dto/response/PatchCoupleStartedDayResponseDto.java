package com.momentree.domain.couple.dto.response;

import com.momentree.domain.couple.entity.Couple;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatchCoupleStartedDayResponseDto(
        Long coupleId,
        LocalDate coupleStartedDay
) {
    public static PatchCoupleStartedDayResponseDto from(Couple patchedCouple) {
        return PatchCoupleStartedDayResponseDto.builder()
                .coupleId(patchedCouple.getId())
                .coupleStartedDay(patchedCouple.getCoupleStartedDay())
                .build();
    }
}
