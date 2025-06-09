package com.momentree.domain.notification.strategy;

import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.dto.request.NotificationRequest;
import com.momentree.domain.user.entity.User;

public interface NotificationStrategy {
    void sendNotification(User receiver, User sender, NotificationRequest request);
    NotificationType getNotificationType();
}
