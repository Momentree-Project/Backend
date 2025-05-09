package com.momentree.domain.schedule.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.schedule.request.PostScheduleRequestDto;
import com.momentree.domain.schedule.request.PatchScheduleRequestDto;
import com.momentree.domain.schedule.response.GetScheduleResponseDto;
import com.momentree.domain.schedule.response.GetAllScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    void postSchedule(PostScheduleRequestDto requestDto, CustomOAuth2User loginUser);
    List<GetAllScheduleResponseDto> getAllSchedules(CustomOAuth2User loginUser);
    GetScheduleResponseDto getSchedule(CustomOAuth2User loginUser, Long scheduleId);
    void deleteSchedule(CustomOAuth2User loginUser, Long scheduleId);
    GetScheduleResponseDto patchSchedule(CustomOAuth2User loginUser, PatchScheduleRequestDto requestDto, Long scheduleId);
}
