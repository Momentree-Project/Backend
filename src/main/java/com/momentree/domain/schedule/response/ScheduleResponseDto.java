package com.momentree.domain.schedule.response;

import java.time.LocalDateTime;

public record ScheduleResponseDto (
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {
}
