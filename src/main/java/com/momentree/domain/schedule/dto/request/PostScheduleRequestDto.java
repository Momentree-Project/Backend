package com.momentree.domain.schedule.dto.request;
import java.time.LocalDateTime;

public record PostScheduleRequestDto(
        Long coupleId,
        Long categoryId,
        String title,
        String content,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAllDay,
        String location
) {}
