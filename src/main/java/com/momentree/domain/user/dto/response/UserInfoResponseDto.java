package com.momentree.domain.user.dto.response;


import com.momentree.domain.user.entity.User;

public record UserInfoResponseDto(
        Long userId,
        String userName,
        String profileImageUrl
) {
    public static UserInfoResponseDto from(User user, String profileImageUrl) {
        return new UserInfoResponseDto(
                user.getId(),
                user.getUsername(),
                profileImageUrl
        );
    }
}
