package com.momentree.domain.post.comment.repository;

import com.momentree.domain.post.comment.entity.Comment;
import com.momentree.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
