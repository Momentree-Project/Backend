package com.momentree.domain.schedule.response;

import java.time.LocalDateTime;

public record ScheduleResponseDto (
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {
    public static ScheduleResponseDto from(ScheduleResponseDto schedule) {
        return new ScheduleResponseDto(
                schedule.title(),
                schedule.startTime(),
                schedule.endTime(),
                schedule.isAllDay(),
                schedule.location()
        );
    }
}
