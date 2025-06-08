package com.momentree.domain.notification.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.notification.dto.request.NotificationRequest;
import com.momentree.domain.notification.dto.response.NotificationResponse;
import com.momentree.domain.notification.entity.Notification;
import com.momentree.domain.notification.repository.NotificationRepository;
import com.momentree.domain.notification.service.NotificationService;
import com.momentree.domain.user.entity.User;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import com.momentree.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final UserValidator userValidator;
    private final NotificationRepository notificationRepository;

    public NotificationResponse getLatestNotification (
            CustomOAuth2User loginUser
    ) {
        User user = userValidator.getUser(loginUser);

        Notification notification =
                notificationRepository.findTopByReceiverOrderByCreatedAtDesc(user)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOTIFICATION_NOT_FOUND));

        return NotificationResponse.from(notification);
    }

    public void postNotification (
            CustomOAuth2User loginUser,
            NotificationRequest request
    ) {
//        User user = userValidator.getUser(loginUser);
//
//        Notification notification = Notification.of (
//                user,
//                request
//        );
//
//        notificationRepository.save(notification);
    }

}
