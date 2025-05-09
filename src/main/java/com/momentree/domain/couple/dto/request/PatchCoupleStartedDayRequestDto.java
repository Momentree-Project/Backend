package com.momentree.domain.couple.dto.request;

import java.time.LocalDate;

public record PatchCoupleStartedDayRequestDto(
        Long coupleId,
        LocalDate coupleStartedDay
) {
}
