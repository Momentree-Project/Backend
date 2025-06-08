package com.momentree.domain.notification.strategy;

import com.momentree.domain.notification.config.SseEmitters;
import com.momentree.domain.notification.dto.request.NotificationRequest;
import com.momentree.domain.notification.entity.Notification;
import com.momentree.domain.notification.repository.NotificationRepository;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public abstract class AbstractNotificationStrategy implements NotificationStrategy {
    protected final NotificationRepository notificationRepository;
    protected final SseEmitters sseEmitters;

    @Override
    public void sendNotification (
            User receiver,
            User sender,
            NotificationRequest request) {
        // 알림 저장
        Notification notification = Notification.of(receiver, sender, request);
        notificationRepository.save(notification);

        // SSE 전송
        sendSseNotification(getEventName(), notification);
    }

    protected void handleEvent (
            User receiver,
            User sender,
            Post post,
            String actionMessage
    ) {
        String redirectUrl = "/posts/" + post.getId();

        // 중복 검증 메서드 호출
        if (!isDuplicateNotification(receiver, sender, redirectUrl)) {
            NotificationRequest request = new NotificationRequest(
                    receiver.getId(),
                    getNotificationType(),
                    sender.getUsername() + actionMessage,
                    redirectUrl
            );

            sendNotification(receiver, sender, request);
        }
    }

    private boolean isDuplicateNotification (
            User receiver,
            User sender,
            String redirectUrl
    ) {
        return notificationRepository.existsByReceiverAndSenderAndTypeAndRedirectUrl(
                receiver,
                sender,
                getNotificationType(),
                redirectUrl
        );
    }

    private void sendSseNotification (
            String eventName,
            Notification notification
    ) {
        Map<String, Object> data = Map.of(
                "id", notification.getId(),
                "content", notification.getContent(),
                "type", notification.getType().toString(),
                "redirectUrl", notification.getRedirectUrl(),
                "isRead", notification.getIsRead(),
                "createdAt", notification.getCreatedAt()
        );
        sseEmitters.noti(eventName, data);
    }

    protected abstract String getEventName();
    protected abstract String getActionMessage();
}

