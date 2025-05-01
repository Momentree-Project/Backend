package com.momentree.domain.auth.oauth2;

import com.momentree.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final User user;
    private final Map<String, Object> attributes;
    private final boolean isFirstLogin;

    public CustomOAuth2User(User user, boolean isFirstLogin) {
        this.user = user;
        this.isFirstLogin = isFirstLogin;
        this.attributes = new HashMap<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Long getUserId() {
        return user.getId();
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }
}
