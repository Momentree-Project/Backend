package com.momentree.domain.schedule.response;

import com.momentree.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record DetailScheduleResponseDto(
        Long categoryId,
        String title,
        String content,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {
    public static DetailScheduleResponseDto from(Schedule schedule) {
        return new DetailScheduleResponseDto(
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
