package com.momentree.domain.schedule.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
import com.momentree.domain.schedule.request.UpdateScheduleRequestDto;
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
            @RequestBody CreateScheduleRequestDto requestDto,
            @AuthenticationPrincipal CustomOAuth2User loginUser
    ) {
        System.out.println("------------------" + loginUser);
        System.out.println("------------------" + loginUser.getUserId());

        // 일정 생성
        scheduleService.createSchedule(requestDto, loginUser);

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

    @PatchMapping
    public BaseResponse<Void> updateSchedule(
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody UpdateScheduleRequestDto requestDto
    ) {
        // 일정 수정
//        scheduleService.updateSchedule(loginUser, requestDto);

        return new BaseResponse<>(ErrorCode.SUCCESS);
    }
}
