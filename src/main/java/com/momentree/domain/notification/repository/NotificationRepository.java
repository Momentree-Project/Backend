package com.momentree.domain.notification.repository;

import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.entity.Notification;
import com.momentree.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByReceiverAndSenderAndTypeAndRedirectUrl(
            User receiver,
            User sender,
            NotificationType type,
            String redirectUrl
    );
}
