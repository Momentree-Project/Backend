package com.momentree.domain.schedule.request;

import java.time.LocalDateTime;

public record UpdateScheduleRequestDto(
        Long categoryId,
        String title,
        String content,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {
}
