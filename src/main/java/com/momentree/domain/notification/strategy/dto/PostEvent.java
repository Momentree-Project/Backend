package com.momentree.domain.notification.strategy.dto;

import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.entity.User;

public record PostEvent(
        User receiver,
        User sender,
        Post post
) {
}
