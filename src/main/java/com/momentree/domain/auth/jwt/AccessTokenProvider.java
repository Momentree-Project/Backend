package com.momentree.domain.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.user.entity.User;
import com.momentree.global.constant.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AccessTokenProvider {
    private Key key;
    private final TokenProperty tokenProperty;

    public AccessTokenProvider(TokenProperty tokenProperty) {
        this.tokenProperty = tokenProperty;
        byte[] byteSecretKey = Decoders.BASE64.decode(tokenProperty.getSecretKey());
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String generateAccessToken(Long userId, String username, String role) {
        return createJwt(userId, username, role, tokenProperty.getAccessTokenExpiration());
    }

    public String createJwt(Long userId, String username, String role, Long expirationTime) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // 서명 검증 및 토큰 파싱
            Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 검증
                    .build()
                    .parseClaimsJws(token); // 유효하지 않으면 예외 발생
            log.info("검증 성공 token : {}", token);
            return true; // 검증 성공
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.info("토큰이 만료되었습니다.");
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            log.info("유효하지 않은 토큰 형식입니다.");
        } catch (io.jsonwebtoken.SignatureException e) {
            log.info("토큰 서명이 유효하지 않습니다.");
        } catch (Exception e) {
            log.info("토큰 검증 중 알 수 없는 오류 발생: " + e.getMessage());
        }
        return false; // 검증 실패
    }

    // 토큰에서 인증 정보 생성
    public Authentication getAuthentication(String accessToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        Long userId = claims.get("userId", Long.class);
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);

        User user = User.builder()
                .id(userId)
                .username(username)
                .role(Role.valueOf(role))
                .build();

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, false);
        return new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
    }

    public void setAccessTokenBody(HttpServletResponse response, String accessToken, CustomOAuth2User customUserDetails) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("accessToken", accessToken);
        responseBody.put("isFirstLogin", customUserDetails.isFirstLogin());
        if(customUserDetails.getCoupleId() == null) {
            responseBody.put("userCode", customUserDetails.getUserCode());
        }

        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }

}
