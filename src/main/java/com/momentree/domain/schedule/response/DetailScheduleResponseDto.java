package com.momentree.domain.schedule.response;

import java.time.LocalDateTime;

public record DetailScheduleResponseDto(
        String title,
        String content,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {
}
