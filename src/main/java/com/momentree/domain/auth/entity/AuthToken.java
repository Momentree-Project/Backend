package com.momentree.domain.auth.entity;

import com.momentree.domain.user.entity.User;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTH_TOKEN")
@AttributeOverride(name = "id", column = @Column(name = "auth_token_id"))
public class AuthToken extends BaseEntity {

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateAuthToken(String refreshToken, LocalDateTime expiresAt) {
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }
}
