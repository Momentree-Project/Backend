package com.momentree.domain.auth.service;

import com.momentree.domain.auth.oauth2.OAuth2UserInfo;
import com.momentree.domain.user.UserCodeManager;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final UserCodeManager userCodeManager;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes={}", oAuth2User.getAttributes());
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userCode = userCodeManager.generateUserCode();
        OAuth2UserInfo userInfo;
        if ("google".equals(registrationId)) {
            userInfo = OAuth2UserInfo.fromGoogle(attributes);
        } else if ("naver".equals(registrationId)) {
            userInfo = OAuth2UserInfo.fromNaver(attributes);
        } else if ("kakao".equals(registrationId)) {
            userInfo = OAuth2UserInfo.fromKakao(attributes);
        } else {
            userInfo = null;
        }

        // DB에 회원가입 되어 있는지 확인 후 가입
        Optional<User> existingUser = userRepository.findByProviderIdAndProvider(userInfo.providerId(), userInfo.provider());
        boolean isFirstLogin = existingUser.isEmpty();

        User user = existingUser.orElseGet(() -> userRepository.save(User.createOAuthUser(userInfo, userCode)));
        return new CustomOAuth2User(user, attributes, isFirstLogin);
    }

}
