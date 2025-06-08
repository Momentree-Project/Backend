package com.momentree.domain.notification.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.notification.dto.request.NotificationRequest;
import com.momentree.domain.notification.dto.response.NotificationResponse;

public interface NotificationService {
    void postNotification(CustomOAuth2User loginUser, NotificationRequest request);
}
