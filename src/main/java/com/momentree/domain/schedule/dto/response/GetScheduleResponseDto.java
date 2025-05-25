package com.momentree.domain.schedule.dto.response;

import com.momentree.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record GetScheduleResponseDto(
        Long id,
        Long categoryId,
        String title,
        String content,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {
    public static GetScheduleResponseDto from(Schedule schedule) {
        return new GetScheduleResponseDto(
                schedule.getId(),
                schedule.getCategoryId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getIsAllDay(),
                schedule.getLocation()
        );
    }
}
