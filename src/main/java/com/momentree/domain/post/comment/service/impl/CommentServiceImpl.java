package com.momentree.domain.post.comment.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.image.repository.ImageRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
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

        // 댓글 작성자의 프로필 이미지 조회
        String profileImageUrl = getProfileImageUrl(user.getId());

        return PostCommentResponse.of(
                comment,
                loginUser.getUserId(),
                profileImageUrl
        );
    }

    @Override
    public List<PostCommentResponse> getAllComments(
            CustomOAuth2User loginUser,
            Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        List<Comment> comments = commentRepository.findAllByPost(post);

        // 모든 댓글 작성자들의 ID 수집
        List<Long> userIds = comments.stream()
                .map(comment -> comment.getUser().getId())
                .distinct()
                .toList();

        // 한 번에 모든 프로필 이미지 조회 (N+1 문제 해결)
        Map<Long, String> userProfileImageMap = new HashMap<>();
        List<com.momentree.domain.image.entity.Image> profileImages =
                imageRepository.findProfileImagesByUserIds(userIds);

        for (com.momentree.domain.image.entity.Image profileImage : profileImages) {
            userProfileImageMap.put(profileImage.getUser().getId(), profileImage.getImageUrl());
        }

        return comments.stream()
                .map(comment -> {
                    String profileImageUrl = userProfileImageMap.get(comment.getUser().getId());
                    return PostCommentResponse.of(comment, loginUser.getUserId(), profileImageUrl);
                })
                .toList();
    }

    // 프로필 이미지 URL을 가져오는 메서드
    private String getProfileImageUrl(Long userId) {
        return imageRepository.findProfileImagesByUserIds(List.of(userId))
                .stream()
                .findFirst()
                .map(com.momentree.domain.image.entity.Image::getImageUrl)
                .orElse(null);
    }
}
