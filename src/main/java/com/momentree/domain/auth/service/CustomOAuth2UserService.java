package com.momentree.domain.auth.service;

import com.momentree.domain.auth.oauth2.OAuth2UserInfo;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.domain.auth.oauth2.PrincipalDetails;
import com.momentree.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes={}", oAuth2User.getAttributes());
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();  //google

        OAuth2UserInfo userInfo;
        if ("google".equals(registrationId)) {
            userInfo = OAuth2UserInfo.fromGoogle(attributes);
        } else {
            userInfo = null;

        }

        // DB에 회원가입 되어 있는지 확인 후 가입
        User user = userRepository.findByProviderIdAndProvider(userInfo.providerId(), userInfo.provider())
                .orElseGet(() -> userRepository.save(User.createOAuthUser(userInfo)));

        return new PrincipalDetails(user, attributes);
    }

}
