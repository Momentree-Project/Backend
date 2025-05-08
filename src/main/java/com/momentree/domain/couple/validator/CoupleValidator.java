package com.momentree.domain.couple.validator;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class CoupleValidator {
    private final UserRepository userRepository;

    public Couple validateAndGetCouple(CustomOAuth2User loginUser) {
        Couple couple = userRepository.findById(loginUser.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER))
                .getCouple();

        if (couple == null) {
            throw new BaseException(ErrorCode.NOT_FOUND_COUPLE);
        }

        return couple;
    }
}
