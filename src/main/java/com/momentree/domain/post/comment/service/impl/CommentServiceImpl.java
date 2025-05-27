package com.momentree.domain.post.comment.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.comment.dto.request.PostCommentRequest;
import com.momentree.domain.post.comment.dto.response.PostCommentResponse;
import com.momentree.domain.post.comment.entity.Comment;
import com.momentree.domain.post.comment.repository.CommentRepository;
import com.momentree.domain.post.comment.service.CommentService;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.post.post.repository.PostRepository;
import com.momentree.domain.user.entity.User;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import com.momentree.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserValidator userValidator;

    @Override
    public PostCommentResponse postComment(
            CustomOAuth2User loginUser,
            PostCommentRequest postCommentRequest) {
        User user = userValidator.getUser(loginUser);

        Post post = postRepository.findById(postCommentRequest.postId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        Comment comment = Comment.of(
                user,
                post,
                postCommentRequest.content(),
                postCommentRequest.level()
        );

        commentRepository.save(comment);

        return PostCommentResponse.of(
                comment,
                loginUser.getUserId()
        );
    }
}
