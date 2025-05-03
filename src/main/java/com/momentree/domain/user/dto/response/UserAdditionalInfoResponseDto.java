package com.momentree.domain.user.dto.response;

import com.momentree.domain.user.entity.User;

import java.time.LocalDate;

public record UserAdditionalInfoResponseDto(Long userId,
                                            LocalDate birth,
                                            String location,
                                            String statusMessage,
                                            String userCode,
                                            Boolean marketingConsent) {
    public static UserAdditionalInfoResponseDto from(User user) {
        return new UserAdditionalInfoResponseDto(
                user.getId(),
                user.getBirth(),
                user.getLocation(),
                user.getStatusMessage(),
                user.getUserCode(),
                user.getMarketingConsent()
        );
    }

}
