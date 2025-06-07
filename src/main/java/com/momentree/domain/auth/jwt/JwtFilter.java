package com.momentree.domain.auth.jwt;

import com.momentree.global.utils.UrlUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AccessTokenProvider tokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(UrlUtils.PermittedUrl).anyMatch(authPath -> pathMatcher.match(authPath, path))
                || path.startsWith("/api/v1/notifications");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = resolveAccessToken(request);
        log.info("jwtFilter 엑세스 토큰 = {}", accessToken);

        //Authorization 헤더 검증
        if (accessToken == null) {
            log.info("엑세스 토큰이 필요합니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰 검증
        if (tokenProvider.validateToken(accessToken)) {
            log.info("엑세스 토큰={}", accessToken);
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            // 토큰 검증 실패 시 401 에러 반환
            log.info("유효하지 않은 토큰입니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            // 필터 체인 진행하지 않음
        }

    }

    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("bearerToken={}", bearerToken);
        return bearerToken != null && bearerToken.startsWith("Bearer ")
                ? bearerToken.substring(7)
                : null;
    }

}
