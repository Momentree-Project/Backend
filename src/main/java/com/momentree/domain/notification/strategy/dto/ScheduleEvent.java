package com.momentree.domain.notification.strategy.dto;

import com.momentree.domain.schedule.entity.Schedule;
import com.momentree.domain.user.entity.User;

public record ScheduleEvent(
        User receiver,
        User sender,
        Schedule schedule
) {
}
