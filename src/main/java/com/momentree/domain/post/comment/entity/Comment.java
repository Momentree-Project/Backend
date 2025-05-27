package com.momentree.domain.post.comment.entity;

import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.entity.User;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "comment")
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",
            foreignKey = @ForeignKey(name = "fk_comment_post",
                    foreignKeyDefinition = "FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE"))
    private Post post;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name= "level", nullable = false)
    private int level = 0;

    public static Comment of(
            User user,
            Post post,
            String content,
            int level
    ) {
        return new Comment (
                user,
                post,
                content,
                level
        );
    }
}
