package com.momentree.domain.notification.entity;

import com.momentree.domain.notification.constant.NotificationType;
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
    private User user;

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

}
