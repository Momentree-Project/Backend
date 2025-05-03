package com.momentree.domain.schedule.service;

import com.momentree.domain.schedule.request.CreateScheduleRequestDto;

public interface ScheduleService {
    void createSchedule(CreateScheduleRequestDto requestDto);
}
