package com.momentree.domain.notification.entity;

import com.momentree.domain.notification.constant.NotificationType;
import com.momentree.domain.notification.dto.request.NotificationRequest;
import com.momentree.domain.user.entity.User;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "notification")
@AttributeOverride(name = "id", column = @Column(name = "notification_id"))
public class Notification  extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User receiver; // 알림 받는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender; // 알림을 발생시킨 사람

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "isRead", nullable = false)
    private Boolean isRead;

    @Column(name = "isSent", nullable = false)
    private Boolean isSent;

    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "redirectUrl")
    private String redirectUrl;

    public void isRead() {
        this.isRead = true;
    }

    public static Notification of(
            User receiver,
            User sender,
            NotificationRequest request
    ) {
        return new Notification (
                receiver,
                sender,
                request.content(),
                false,
                false,
                request.type(),
                request.redirectUrl()
        );
    }
}
