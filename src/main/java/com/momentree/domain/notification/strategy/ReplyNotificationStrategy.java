package com.momentree.domain.notification.strategy;

import com.momentree.domain.notification.config.SseEmitters;
import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.repository.NotificationRepository;
import com.momentree.domain.notification.strategy.dto.ReplyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ReplyNotificationStrategy extends AbstractNotificationStrategy {
    public ReplyNotificationStrategy(NotificationRepository notificationRepository, SseEmitters sseEmitters) {
        super(notificationRepository, sseEmitters);
    }

    @EventListener
    public void handleReplyEvent(ReplyEvent event) {
        handleEvent(
                event.receiver(),  // receiver
                event.sender(),    // sender
                event.post(),        // post
                getActionMessage()   // actionMessage
        );
    }

    @Override
    protected String getEventName() {
        return "reply-notification";
    }

    @Override
    protected String getActionMessage() {
        return "님이 회원님의 댓글에 답글을 남겼습니다.";
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.COMMENT;
    }
}
