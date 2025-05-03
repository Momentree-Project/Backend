package com.momentree.domain.couple.service;

import com.momentree.domain.couple.dto.request.CoupleConnectRequestDto;
import com.momentree.domain.couple.dto.response.CoupleConnectResponseDto;

public interface CoupleService {
    CoupleConnectResponseDto connectCouple(Long userId, CoupleConnectRequestDto requestDto);
}
