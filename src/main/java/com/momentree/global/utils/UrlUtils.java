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
            "/oauth2/**", // OAuth2 관련 URL
            "/api/v1/auth/refresh-token",

            // 테스트를 위해 임시로 모든 URL 허용 (나중에 삭제)
            "/api/v1/**", // 모든 API URL
    };
}
