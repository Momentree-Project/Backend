package com.momentree.domain.user.dto.request;

public record UserAdditionalInfoRequestDto(
        String birth,
        String location,
        String statusMessage,
        Boolean marketingConsent
) {
}
