package com.momentree.domain.schedule.service;

import com.momentree.domain.schedule.entity.Schedule;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;

public interface ScheduleService {
    Schedule createSchedule(CreateScheduleRequestDto requestDto);
}
