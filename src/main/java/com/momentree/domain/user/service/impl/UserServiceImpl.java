package com.momentree.domain.user.service.impl;

import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.user.dto.request.PatchMarketingConsentRequestDto;
import com.momentree.domain.user.dto.request.PatchPersonalRequestDto;
import com.momentree.domain.user.dto.request.PatchProfileRequestDto;
import com.momentree.domain.user.dto.response.*;
import com.momentree.domain.user.entity.User;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
import com.momentree.domain.user.service.UserService;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserAdditionalInfoResponseDto patchUserAdditionalInfo(Long userId, UserAdditionalInfoRequestDto requestDto) {
        User findedUser = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        findedUser.updateUserAdditionalInfo(requestDto);
        User updatedUser = userRepository.save(findedUser);
        return UserAdditionalInfoResponseDto.from(updatedUser);
    }

    @Override
    public GetProfileResponseDto getMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        Couple couple = user.getCouple();
        if (couple == null) new BaseException(ErrorCode.NOT_CONNECTED_COUPLE);
        LocalDate coupleStartedDay = couple.getCoupleStartedDay();
        String partnerEmail = userRepository.findPartnerEmailByCoupleAndUserId(couple, userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_PARTNER));


        return GetProfileResponseDto.of(user, coupleStartedDay, partnerEmail);
    }

    @Override
    public PatchProfileResponseDto patchMyProfile(Long userId, PatchProfileRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        user.patchMyProfile(requestDto);
        User patchedUser = userRepository.save(user);
        return PatchProfileResponseDto.from(patchedUser);
    }

    @Override
    public PatchPersonalResponseDto patchMyPersonal(Long userId, PatchPersonalRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        user.patchMyPersonal(requestDto);
        User patchedUser = userRepository.save(user);
        return PatchPersonalResponseDto.from(patchedUser);
    }

    @Override
    public PatchMarketingConsentResponseDto patchMyMarketingConsent(Long userId, PatchMarketingConsentRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        user.patchMyMarketingConsent(requestDto);
        User patchedUser = userRepository.save(user);
        return PatchMarketingConsentResponseDto.from(patchedUser);
    }

    @Override
    public Void deleteMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        user.inactivate();
        userRepository.save(user);
        return null;
    }

    @Override
    public Void recoverMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        user.recover();
        userRepository.save(user);
        return null;
    }


}
