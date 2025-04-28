package com.momentree.domain.auth.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtProperty {
    private String secretKey;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
}
