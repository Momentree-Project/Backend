package com.momentree.domain.schedule.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
import com.momentree.domain.schedule.request.UpdateScheduleRequestDto;
import com.momentree.domain.schedule.response.DetailScheduleResponseDto;
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
    public BaseResponse<Void> postSchedule (
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody CreateScheduleRequestDto requestDto
    ) {
        // 일정 생성
        scheduleService.postSchedule(requestDto, loginUser);

        return new BaseResponse<>(ErrorCode.SUCCESS);
    }

    @GetMapping
    public BaseResponse<List<ScheduleResponseDto>> getAllSchedules(
            @AuthenticationPrincipal CustomOAuth2User loginUser
    ) {
        // 로그인한 사용자 커플의 일정 목록을 조회
        return new BaseResponse<>(scheduleService.getAllSchedules(loginUser));
    }

    @GetMapping("/detail")
    public BaseResponse<DetailScheduleResponseDto> getSchedule(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestParam Long scheduleId
    ) {
        // 로그인한 사용자 커플의 일정 상세 조회
        return new BaseResponse<>(scheduleService.getSchedule(loginUser, scheduleId));
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

    @PatchMapping
    public BaseResponse<DetailScheduleResponseDto> patchSchedule(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody UpdateScheduleRequestDto requestDto,
            @RequestParam Long scheduleId
    ) {
        // 일정 수정
        return new BaseResponse<>(scheduleService.patchSchedule(loginUser, requestDto, scheduleId));
    }
}
