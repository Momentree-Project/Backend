package com.momentree.domain.auth.service;

public interface AuthService {
    String refreshAccessToken(String refreshToken);
}
