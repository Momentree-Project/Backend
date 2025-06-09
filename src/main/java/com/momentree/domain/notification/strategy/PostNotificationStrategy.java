package com.momentree.domain.notification.strategy;

import com.momentree.domain.notification.config.SseEmitters;
import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.repository.NotificationRepository;
import com.momentree.domain.notification.strategy.dto.PostEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PostNotificationStrategy extends AbstractNotificationStrategy {
    public PostNotificationStrategy(NotificationRepository notificationRepository, SseEmitters sseEmitters) {
        super(notificationRepository, sseEmitters);
    }

    @EventListener
    public void handlePostEvent(PostEvent event) {
        handleEvent(
                event.receiver(),  // receiver
                event.sender(),    // sender
                event.post(),        // post
                getActionMessage()   // actionMessage
        );
    }

    @Override
    protected String getEventName() {
        return "post-notification";
    }

    @Override
    protected String getActionMessage() {
        return "님이 새로운 게시글을 작성했습니다.";
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.POST;
    }
}
