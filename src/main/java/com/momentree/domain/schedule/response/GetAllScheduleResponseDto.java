package com.momentree.domain.schedule.response;

import com.momentree.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record GetAllScheduleResponseDto(
        Long id,
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {
    public static GetAllScheduleResponseDto from(Schedule schedule) {
        return new GetAllScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getIsAllDay(),
                schedule.getLocation()
        );
    }
}
