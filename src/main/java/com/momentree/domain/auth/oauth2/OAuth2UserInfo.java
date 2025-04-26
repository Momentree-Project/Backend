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
}
