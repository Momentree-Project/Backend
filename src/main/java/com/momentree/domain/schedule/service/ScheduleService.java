package com.momentree.domain.schedule.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
import com.momentree.domain.schedule.response.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    void createSchedule(CreateScheduleRequestDto requestDto, CustomOAuth2User loginUser);
    List<ScheduleResponseDto> retrieveSchedule(CustomOAuth2User loginUser);
    void deleteSchedule(CustomOAuth2User loginUser, Long scheduleId);
}
