package com.momentree.domain.couple.service.impl;

import com.momentree.domain.couple.dto.request.CoupleConnectRequestDto;
import com.momentree.domain.couple.dto.response.CoupleConnectResponseDto;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.couple.repository.CoupleRepository;
import com.momentree.domain.couple.service.CoupleService;
import com.momentree.domain.user.entity.User;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoupleServiceImpl implements CoupleService {

    private final UserRepository userRepository;
    private final CoupleRepository coupleRepository;

    @Override
    @Transactional
    public CoupleConnectResponseDto connectCouple(Long userId, CoupleConnectRequestDto requestDto) {
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        User user2 = userRepository.findByUserCode(requestDto.userCode())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        log.info("user1={}", user1.getId());
        log.info("user2={}", user2.getId());

        // 자신의 코드로 연결하려는 경우
        if (requestDto.userCode().equals(user1.getUserCode())) {
            throw new BaseException(ErrorCode.CANNOT_CONNECT_SELF);
        }

        // 이미 커플이 연결된 상태인지 확인
        if (user1.getCouple() != null || user2.getCouple() != null) {
            throw new BaseException(ErrorCode.ALREADY_CONNECTED_COUPLE);
        }

        Couple couple = Couple.from(LocalDate.parse(requestDto.coupleStartedDay()));
        Couple savedCouple = coupleRepository.save(couple);

        user1.assignCouple(savedCouple);
        user2.assignCouple(savedCouple);

        // 변경된 User 저장
        userRepository.save(user1);
        userRepository.save(user2);

        return CoupleConnectResponseDto.from(user1.getId(), user2.getId(), savedCouple.getCoupleStartedDay());
    }
}
