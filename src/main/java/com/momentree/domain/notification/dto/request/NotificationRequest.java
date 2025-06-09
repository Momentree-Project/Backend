package com.momentree.domain.notification.dto.request;

import com.momentree.domain.notification.constant.NotificationType;

public record NotificationRequest(
    Long memberId,
    NotificationType type,
    String content,
    String redirectUrl
) {
}
