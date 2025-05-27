package com.momentree.domain.post.comment.repository;

import com.momentree.domain.post.comment.entity.Comment;
import com.momentree.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);

    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.user u " +
            "LEFT JOIN FETCH Image i ON i.user = u AND i.post IS NULL " +
            "WHERE c.post = :post " +
            "ORDER BY c.createdAt ASC")
    List<Comment> findAllByPostWithProfileImage(@Param("post") Post post);
}
