package com.momentree.domain.user.dto.request;

import java.time.LocalDate;

public record PatchPersonalRequestDto(
        LocalDate birth,
        String location
) {
}
