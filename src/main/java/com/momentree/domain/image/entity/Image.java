package com.momentree.domain.image.entity;

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
@Table(name = "image")
@AttributeOverride(name = "id", column = @Column(name = "image_id"))
public class Image extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",
            foreignKey = @ForeignKey(name = "fk_image_post",
                    foreignKeyDefinition = "FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE"))
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    // 프로필 사진을 위한 정적 팩토리 메서드
    public static Image of(String imageUrl, User user) {
        return Image.builder()
                .imageUrl(imageUrl)
                .user(user)
                .build();
    }

    // 게시글 연결을 위한 정적 팩토리 메서드
    public static Image of(String imageUrl, Post post) {
        return Image.builder()
                .imageUrl(imageUrl)
                .post(post)
                .build();
    }
}
