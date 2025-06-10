package com.momentree.domain.notification.strategy;

import com.momentree.domain.notification.config.SseEmitters;
import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.repository.NotificationRepository;
import com.momentree.domain.notification.strategy.dto.LikeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
@Component
public class LikeNotificationStrategy extends AbstractNotificationStrategy {

    public LikeNotificationStrategy(NotificationRepository notificationRepository, SseEmitters sseEmitters) {
        super(notificationRepository, sseEmitters);
    }

    @EventListener
    public void handleLikeEvent(LikeEvent event) {
        handleEvent(
                event.receiver(),  // receiver
                event.sender(),    // sender
                event.post(),        // post
                getActionMessage()   // actionMessage
        );
    }

    @Override
    protected String getEventName() {
        return "like-notification";
    }

    @Override
    protected String getActionMessage() {
        return "님이 회원님의 게시글을 좋아합니다.";
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.LIKE;
    }
}
