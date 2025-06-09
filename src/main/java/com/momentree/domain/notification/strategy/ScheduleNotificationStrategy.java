package com.momentree.domain.notification.strategy;

import com.momentree.domain.notification.config.SseEmitters;
import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.repository.NotificationRepository;
import com.momentree.domain.notification.strategy.dto.ScheduleEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ScheduleNotificationStrategy extends AbstractNotificationStrategy {
    public ScheduleNotificationStrategy(NotificationRepository notificationRepository, SseEmitters sseEmitters) {
        super(notificationRepository, sseEmitters);
    }

    @EventListener
    public void handleScheduleEvent(ScheduleEvent event) {
        handleEvent(
                event.receiver(),  // receiver
                event.sender(),    // sender
                event.schedule(),        // schedule
                getActionMessage()   // actionMessage
        );
    }

    @Override
    protected String getEventName() {
        return "schedule-notification";
    }

    @Override
    protected String getActionMessage() {
        return "님이 새로운 일정을 추가했습니다.";
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.SCHEDULE;
    }
}
