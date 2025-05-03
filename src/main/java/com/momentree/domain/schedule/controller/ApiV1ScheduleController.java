package com.momentree.domain.schedule.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
import com.momentree.domain.schedule.response.ScheduleResponseDto;
import com.momentree.domain.schedule.service.ScheduleService;
import com.momentree.global.exception.BaseResponse;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ApiV1ScheduleController {
    private final ScheduleService scheduleService;
    @PostMapping
    public BaseResponse<Void> createSchedule (
            @RequestBody CreateScheduleRequestDto requestDto
    ) {
        // 일정 생성
        scheduleService.createSchedule(requestDto);

        return new BaseResponse<>(ErrorCode.SUCCESS);
    }

    @GetMapping
    public BaseResponse<List<ScheduleResponseDto>> retrieveSchedule(
            @AuthenticationPrincipal CustomOAuth2User loginUser
    ) {
        // 로그인한 사용자 커플의 일정 목록을 조회
        return new BaseResponse<>(scheduleService.retrieveSchedule(loginUser));
    }

    @DeleteMapping
    public BaseResponse<Void> deleteSchedule(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestParam Long scheduleId
    ) {
        // 일정 삭제
        scheduleService.deleteSchedule(loginUser, scheduleId);

        return new BaseResponse<>(ErrorCode.SUCCESS);
    }
}
