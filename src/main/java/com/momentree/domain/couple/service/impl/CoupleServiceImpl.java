package com.momentree.domain.couple.service.impl;

import com.momentree.domain.couple.dto.request.CoupleConnectRequestDto;
import com.momentree.domain.couple.dto.request.PatchCoupleStartedDayRequestDto;
import com.momentree.domain.couple.dto.response.CoupleConnectResponseDto;
import com.momentree.domain.couple.dto.response.PatchCoupleStartedDayResponseDto;
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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoupleServiceImpl implements CoupleService {

    private final UserRepository userRepository;
    private final CoupleRepository coupleRepository;

    @Override
    @Transactional
    public CoupleConnectResponseDto connectCouple(Long userId, CoupleConnectRequestDto requestDto) {
        User me = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        User partner = userRepository.findByUserCode(requestDto.userCode())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        // 자신의 코드로 연결하려는 경우
        if (requestDto.userCode().equals(me.getUserCode())) {
            throw new BaseException(ErrorCode.CANNOT_CONNECT_SELF);
        }

        // 이미 커플이 연결된 상태인지 확인
        if (me.getCouple() != null || partner.getCouple() != null) {
            throw new BaseException(ErrorCode.ALREADY_CONNECTED_COUPLE);
        }

        Couple couple = Couple.from(LocalDate.parse(requestDto.coupleStartedDay()));
        Couple savedCouple = coupleRepository.save(couple);

        me.assignCouple(savedCouple);
        partner.assignCouple(savedCouple);

        //userCode 삭제
        me.deleteUserCode();
        partner.deleteUserCode();

        // 변경된 User 저장
        userRepository.save(me);
        userRepository.save(partner);

        return CoupleConnectResponseDto.of(savedCouple.getId(), me.getId(), partner.getId(), savedCouple.getCoupleStartedDay());
    }

    @Override
    @Transactional
    public void disconnectCouple(Long userId, Long coupleId) {
        User me = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        Couple couple = me.getCouple();
        if (couple == null) throw new BaseException(ErrorCode.NOT_CONNECTED_COUPLE);

        List<User> coupleUsers = userRepository.findByCouple(couple);
        for (User user : coupleUsers) {
            user.disconnectCouple();
        }
        userRepository.flush();
        coupleRepository.delete(couple);
    }

    @Override
    public PatchCoupleStartedDayResponseDto patchCoupleStartedDay(Long userId, PatchCoupleStartedDayRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        if (!requestDto.coupleId().equals(user.getCouple().getId()))
            throw new BaseException(ErrorCode.NOT_FOUND_COUPLE);
        Couple findedCouple = coupleRepository.findById(requestDto.coupleId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_COUPLE));

        findedCouple.patchCoupleStartedDay(requestDto);
        Couple patchedCouple = coupleRepository.save(findedCouple);
        return PatchCoupleStartedDayResponseDto.from(patchedCouple);
    }
}
