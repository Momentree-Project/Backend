package com.momentree.domain.notification.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.notification.dto.request.NotificationRequest;
import com.momentree.domain.notification.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Page<NotificationResponse> patchAllNotification(CustomOAuth2User loginUser, Pageable pageable);
    NotificationResponse patchNotification(CustomOAuth2User loginUser, Long notificationId);
    void postNotification(CustomOAuth2User loginUser, NotificationRequest request);
    NotificationResponse getLatestNotification(CustomOAuth2User loginUser);
    Page<NotificationResponse> getAllNotification(CustomOAuth2User loginUser, Pageable apageable);
}
