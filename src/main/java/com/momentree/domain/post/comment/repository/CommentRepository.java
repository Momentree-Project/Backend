package com.momentree.domain.post.comment.repository;

import com.momentree.domain.post.comment.entity.Comment;
import com.momentree.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByPost(Post post);
    @Query("SELECT c.post.id, COUNT(c) FROM Comment c WHERE c.post.id IN :postIds GROUP BY c.post.id")
    List<Object[]> countCommentsByPostIds(@Param("postIds") List<Long> postIds);

    // 계층 구조로 정렬된 댓글 조회
    @Query("SELECT c FROM Comment c WHERE c.post = :post " +
            "ORDER BY COALESCE(c.parent.id, c.id), c.parent.id NULLS FIRST, c.createdAt ASC")
    List<Comment> findAllByPostOrderByHierarchy(@Param("post") Post post);
}
