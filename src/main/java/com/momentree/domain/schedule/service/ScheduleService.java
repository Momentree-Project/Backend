package com.momentree.domain.schedule.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
import com.momentree.domain.schedule.request.UpdateScheduleRequestDto;
import com.momentree.domain.schedule.response.DetailScheduleResponseDto;
import com.momentree.domain.schedule.response.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    void postSchedule(CreateScheduleRequestDto requestDto, CustomOAuth2User loginUser);
    List<ScheduleResponseDto> getAllSchedules(CustomOAuth2User loginUser);
    DetailScheduleResponseDto getSchedule(CustomOAuth2User loginUser, Long scheduleId);
    void deleteSchedule(CustomOAuth2User loginUser, Long scheduleId);
    DetailScheduleResponseDto patchSchedule(CustomOAuth2User loginUser, UpdateScheduleRequestDto requestDto, Long scheduleId);
}
