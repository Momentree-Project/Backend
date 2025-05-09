package com.momentree.domain.couple.service;

import com.momentree.domain.couple.dto.request.CoupleConnectRequestDto;
import com.momentree.domain.couple.dto.request.PatchCoupleStartedDayRequestDto;
import com.momentree.domain.couple.dto.response.CoupleConnectResponseDto;
import com.momentree.domain.couple.dto.response.CoupleDisconnectResponseDto;
import com.momentree.domain.couple.dto.response.PatchCoupleStartedDayResponseDto;

public interface CoupleService {
    CoupleConnectResponseDto connectCouple(Long userId, CoupleConnectRequestDto requestDto);
    CoupleDisconnectResponseDto disconnectCouple(Long userId, Long coupleId);
    PatchCoupleStartedDayResponseDto patchCoupleStartedDay(Long userId, PatchCoupleStartedDayRequestDto requestDto);
}
