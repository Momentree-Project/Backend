package com.momentree.domain.user.dto.response;

import com.momentree.domain.user.entity.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatchPersonalResponseDto(
        LocalDate birth,
        String location
) {
    public static PatchPersonalResponseDto from(User patchedUser) {
        return PatchPersonalResponseDto.builder()
                .birth(patchedUser.getBirth())
                .location(patchedUser.getLocation())
                .build();
    }
}
