package com.momentree.domain.schedule.request;
import java.time.LocalDateTime;

public record CreateScheduleRequestDto(
        Long coupleId,
        Long categoryId,
        String title,
        String content,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {}
