package com.momentree.domain.user.dto.request;

import java.time.LocalDate;

public record PatchProfileRequestDto(
        String username,
        LocalDate birth,
        String location,
        Boolean marketingConsent,
        String statusMessage
) {
}
