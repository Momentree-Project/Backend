package com.momentree.domain.post.like.repository;

import com.momentree.domain.post.like.entity.Likes;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Boolean existsByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);

}
