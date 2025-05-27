package com.momentree.domain.post.like.entity;

import com.momentree.domain.post.post.entity.Post;
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
@Table(name = "likes")
@AttributeOverride(name = "id", column = @Column(name = "like_id"))
public class Likes extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",
            foreignKey = @ForeignKey(name = "fk_likes_post",
                    foreignKeyDefinition = "FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE"))
    private Post post;

    public static Likes of(User user, Post post) {
        return Likes.builder()
                .user(user)
                .post(post)
                .build();
    }
}