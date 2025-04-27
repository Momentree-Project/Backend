package com.momentree.domain.user.entity;

import com.momentree.domain.auth.oauth2.OAuth2UserInfo;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.global.entity.BaseEntity;
import com.momentree.global.constant.Role;
import com.momentree.global.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseEntity {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birth", nullable = true)
    private LocalDate birth;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "user_code", nullable = false)
    private String userCode;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @ManyToOne
    @JoinColumn(name = "couple_id")
    private Couple couple;

    public static User createOAuthUser(OAuth2UserInfo userInfo, String userCode) {
        return User.builder()
                .provider(userInfo.provider())
                .providerId(userInfo.providerId())
                .email(userInfo.email())
                .username(userInfo.name())
                .userCode(userCode)
                .status(Status.ACTIVE)
                .role(Role.USER)
                .build();
    }

}
