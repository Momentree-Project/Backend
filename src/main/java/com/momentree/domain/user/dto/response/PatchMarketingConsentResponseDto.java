package com.momentree.domain.user.dto.response;

import com.momentree.domain.user.entity.User;
import lombok.Builder;

@Builder
public record PatchMarketingConsentResponseDto(
        boolean marketingConsent
) {
    public static PatchMarketingConsentResponseDto from(User patchedUser) {
        return PatchMarketingConsentResponseDto.builder()
                .marketingConsent(patchedUser.getMarketingConsent())
                .build();
    }
}
