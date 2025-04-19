package com.momentree.domain.image.entity;

import com.momentree.domain.post.post.entity.Post;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * packageName    : com.momentree.domain.image.entity
 * fileName       : Image
 * author         : sungjun
 * date           : 2025-04-19
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-19        kyd54       최초 생성
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "image")
@AttributeOverride(name = "id", column = @Column(name = "image_id"))
public class Image extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String imageUrl;

}
