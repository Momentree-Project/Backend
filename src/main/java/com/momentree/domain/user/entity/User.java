package com.momentree.domain.user.entity;

import com.momentree.domain.auth.oauth2.OAuth2UserInfo;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.user.dto.request.PatchMarketingConsentRequestDto;
import com.momentree.domain.user.dto.request.PatchPersonalRequestDto;
import com.momentree.domain.user.dto.request.PatchProfileRequestDto;
import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "location")
    private String location;

    @Column(name = "marketingConsent")
    private Boolean marketingConsent;

    @Column(name = "statusMessage")
    private String statusMessage;

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

    public void assignCouple(Couple couple) {
        this.couple = couple;
    }

    public void updateUserAdditionalInfo(UserAdditionalInfoRequestDto requestDto) {
        if (requestDto.birth() != null) this.birth = LocalDate.parse(requestDto.birth());
        if (requestDto.location() != null) this.location = requestDto.location();
        if (requestDto.statusMessage() != null) this.statusMessage = requestDto.statusMessage();
        this.marketingConsent = requestDto.marketingConsent();
    }

    public void patchMyProfile(PatchProfileRequestDto requestDto) {
        this.username = requestDto.username();
        this.statusMessage = requestDto.statusMessage();
    }

    public void patchMyPersonal(PatchPersonalRequestDto requestDto) {
        this.birth = requestDto.birth();
        this.location = requestDto.location();
    }

    public void patchMyMarketingConsent(PatchMarketingConsentRequestDto requestDto) {
        this.marketingConsent = requestDto.marketingConsent();
    }

    public void deleteUserCode() {
        this.userCode = null;
    }

    public void disconnectCouple() {
        this.couple = null;
    }

    public void inactivate() {
        this.status = Status.INACTIVE;
    }

    public void recover() {
        this.status = Status.ACTIVE;
    }

    public void createUserCode(String userCode) {
        this.userCode = userCode;
    }
}
