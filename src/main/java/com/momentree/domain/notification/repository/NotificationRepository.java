package com.momentree.domain.notification.repository;

import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.entity.Notification;
import com.momentree.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByReceiverAndSenderAndTypeAndRedirectUrl(
            User receiver,
            User sender,
            NotificationType type,
            String redirectUrl
    );

    Optional<Notification> findTopByReceiverOrderByCreatedAtDesc(User user);

    Page<Notification> findByReceiverOrderByCreatedAtDesc(User receiver, Pageable pageable);

    Page<Notification> findByReceiverAndIsReadFalseOrderByCreatedAtDesc(User receiver, Pageable pageable);
}
