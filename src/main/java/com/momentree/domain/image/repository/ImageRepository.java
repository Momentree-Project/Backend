package com.momentree.domain.image.repository;

import com.momentree.domain.image.entity.Image;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUserAndPostIsNull(User user);
    List<Image> findByPostIdIn(Collection<Long> postIds);
    List<Image> findByPost(Post post);
    Optional<Image> findByImageUrlAndPost(String imageUrl, Post post);

    // 여러 사용자의 프로필 이미지를 한 번에 조회
    @Query("SELECT i FROM Image i WHERE i.user.id IN :userIds AND i.post IS NULL")
    List<Image> findProfileImagesByUserIds(@Param("userIds") List<Long> userIds);
}
