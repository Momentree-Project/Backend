package com.momentree.domain.schedule.controller;

import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
import com.momentree.domain.schedule.service.ScheduleService;
import com.momentree.global.exception.BaseResponse;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ApiV1ScheduleController {
    private final ScheduleService scheduleService;
    @PostMapping
    public BaseResponse<Void> createSchedule (
            @RequestBody CreateScheduleRequestDto requestDto) {

        scheduleService.createSchedule(requestDto);

        return new BaseResponse<>(ErrorCode.SUCCESS);
    }
}
