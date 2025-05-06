package com.momentree.domain.user.dto.response;

import com.momentree.domain.user.entity.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatchProfileResponseDto(
        String username,
        LocalDate birth,
        String location,
        Boolean marketingConsent,
        String statusMessage
) {

    public static PatchProfileResponseDto from(User savedUser) {
        return PatchProfileResponseDto.builder()
                .username(savedUser.getUsername())
                .birth(savedUser.getBirth())
                .location(savedUser.getLocation())
                .marketingConsent(savedUser.getMarketingConsent())
                .statusMessage(savedUser.getStatusMessage())
                .build();
    }

}
