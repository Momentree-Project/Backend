package com.momentree.domain.user.dto.response;

import com.momentree.domain.user.entity.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetProfileResponseDto(
        Long userId,
        String username,
        String email,
        LocalDate birth,
        String location,
        Boolean marketingConsent,
        String statusMessage,
        LocalDate coupleStartedDay,
        String partnerEmail
) {
    public static GetProfileResponseDto of(User user, LocalDate coupleStartedDay, String partnerEmail) {
        return GetProfileResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .birth(user.getBirth())
                .location(user.getLocation())
                .marketingConsent(user.getMarketingConsent())
                .statusMessage(user.getStatusMessage())
                .coupleStartedDay(coupleStartedDay)
                .partnerEmail(partnerEmail)
                .build();
    }

}
