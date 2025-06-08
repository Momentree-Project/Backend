package com.momentree.domain.notification.dto.response;

import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.entity.Notification;

import java.time.LocalDateTime;

public record NotificationResponse (
    Long id,
    String content,
    String redirectUrl,
    NotificationType type,
    LocalDateTime createdAt
) {
    public static NotificationResponse from(
            Notification notification
    ) {
        return new NotificationResponse(
                notification.getId(),
                notification.getContent(),
                notification.getRedirectUrl(),
                notification.getType(),
                notification.getCreatedAt()
        );
    }
}
