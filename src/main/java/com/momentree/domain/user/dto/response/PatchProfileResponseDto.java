package com.momentree.domain.user.dto.response;

import com.momentree.domain.user.entity.User;
import lombok.Builder;


@Builder
public record PatchProfileResponseDto(
        String username,
        String statusMessage
) {

    public static PatchProfileResponseDto from(User patchedUser) {
        return PatchProfileResponseDto.builder()
                .username(patchedUser.getUsername())
                .statusMessage(patchedUser.getStatusMessage())
                .build();
    }

}
