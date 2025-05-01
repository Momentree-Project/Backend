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
            "/oauth2/**" // OAuth2 관련 URL
    };
}
