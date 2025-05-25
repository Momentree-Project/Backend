package com.momentree.domain.schedule.dto.response;

import com.momentree.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record GetAllScheduleResponseDto(
        Long id,
        Long categoryId,
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {
    public static GetAllScheduleResponseDto from(Schedule schedule) {
        return new GetAllScheduleResponseDto(
                schedule.getId(),
                schedule.getCategoryId(),
                schedule.getTitle(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getIsAllDay(),
                schedule.getLocation()
        );
    }
}
