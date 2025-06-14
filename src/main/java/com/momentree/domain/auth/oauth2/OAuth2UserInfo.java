package com.momentree.domain.auth.oauth2;

import java.util.Map;

public record OAuth2UserInfo(
        String providerId,
        String provider,
        String email,
        String name
) {
    public static OAuth2UserInfo fromGoogle(Map<String, Object> attributes) {
        return new OAuth2UserInfo(
                (String) attributes.get("sub"),
                "google",
                (String) attributes.get("email"),
                (String) attributes.get("name")
        );
    }

    public static OAuth2UserInfo fromNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return new OAuth2UserInfo(
                (String) response.get("id"),
                "naver",
                (String) response.get("email"),
                (String) response.get("name")
        );

    }

    public static OAuth2UserInfo fromKakao(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return new OAuth2UserInfo(
                String.valueOf(attributes.get("id")),
                "kakao",
                (String) kakaoAccount.get("email"),
                (String) properties.get("nickname")
        );

    }
}
