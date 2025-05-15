package com.momentree.domain.schedule.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.schedule.dto.request.PostScheduleRequestDto;
import com.momentree.domain.schedule.dto.request.PatchScheduleRequestDto;
import com.momentree.domain.schedule.dto.response.GetScheduleResponseDto;
import com.momentree.domain.schedule.dto.response.GetAllScheduleResponseDto;
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
            @RequestBody PostScheduleRequestDto requestDto
    ) {
        // 일정 생성
        scheduleService.postSchedule(requestDto, loginUser);

        return new BaseResponse<>(ErrorCode.SUCCESS);
    }

    @GetMapping
    public BaseResponse<List<GetAllScheduleResponseDto>> getAllSchedules(
            @AuthenticationPrincipal CustomOAuth2User loginUser
    ) {
        // 로그인한 사용자 커플의 일정 목록을 조회
        return new BaseResponse<>(scheduleService.getAllSchedules(loginUser));
    }

    @GetMapping("/{schedule-id}")
    public BaseResponse<GetScheduleResponseDto> getSchedule(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PathVariable("schedule-id") Long scheduleId
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
    public BaseResponse<GetScheduleResponseDto> patchSchedule(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody PatchScheduleRequestDto requestDto,
            @RequestParam Long scheduleId
    ) {
        // 일정 수정
        return new BaseResponse<>(scheduleService.patchSchedule(loginUser, requestDto, scheduleId));
    }
}
