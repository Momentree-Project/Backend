package com.momentree.global.utils;

public class UrlUtils {
    public static final String[] PermittedUrl = {
            "/favicon.ico",
            "/",
            "/error",
            "/css/**",
            "/js/**",
            "/images/**",
            "/auth/**",  // 로그인/회원가입 관련 URL
            "/api/v1/auth/refresh-token",
            "/oauth2/**", // OAuth2 관련 URL
            "/oauth2/authorization/**",
            "/login/oauth2/**",
            "/login/oauth2/code/**", // 추가
            "/login", // 추가
            "/api/v1/notifications/connects"
    };
}
