package com.momentree.domain.schedule.request;

public record UpdateScheduleRequestDto(
        String title,
        String content,
        String startTime,
        String endTime,
        Boolean isAllDay,
        String location
) {
}
