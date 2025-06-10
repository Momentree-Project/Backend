package com.momentree.domain.post.comment.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.image.repository.ImageRepository;
import com.momentree.domain.notification.strategy.dto.CommentEvent;
import com.momentree.domain.notification.strategy.dto.ReplyEvent;
import com.momentree.domain.post.comment.dto.request.PatchCommentRequest;
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
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public PostCommentResponse postComment (
            CustomOAuth2User loginUser,
            PostCommentRequest postCommentRequest) {
        User user = userValidator.getUser(loginUser);

        Post post = postRepository.findById(postCommentRequest.postId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        Comment parent = null;

        // 대댓글인 경우 부모 댓글 조회
        if (postCommentRequest.parentId() != null) {
            parent = commentRepository.findById(postCommentRequest.parentId())
                    .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_COMMENT));

            // 부모 댓글이 같은 게시글에 속하는지 검증
            if (!parent.getPost().getId().equals(post.getId())) {
                throw new BaseException(ErrorCode.INVALID_PARENT_COMMENT);
            }

            // 대댓글 알림 이벤트 발행 (자기 자신이 아닌 경우에만)
            if (!user.getId().equals(parent.getUser().getId())) {
                eventPublisher.publishEvent(new ReplyEvent(
                        parent.getUser(), // 원댓글 작성자 (알림 받을 사람)
                        user,           // 대댓글 작성자
                        post
                ));
            }
        }

        Comment comment = Comment.of(
                user,
                post,
                postCommentRequest.content(),
                postCommentRequest.level(),
                parent
        );

        commentRepository.save(comment);

        // 댓글 작성자의 프로필 이미지 조회
        String profileImageUrl = getProfileImageUrl(user.getId());

        // 댓글 알림 이벤트 발행 (자기 자신이 아닌 경우에만)
        if (!user.getId().equals(post.getUser().getId())) {
            eventPublisher.publishEvent(new CommentEvent(
                    post.getUser(), // 원댓글 작성자 (알림 받을 사람)
                    user,           // 대댓글 작성자
                    post
            ));
        }

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

        // 계층 구조로 정렬된 댓글 조회
        List<Comment> comments = commentRepository.findAllByPostOrderByHierarchy(post);

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

    @Override
    public void deleteComment(
            CustomOAuth2User loginUser,
            Long commentId
    ) {
        User user = userValidator.getUser(loginUser);
        Comment comment = findCommentById(commentId);

        // 댓글 작성자 본인인지 검증
        validateCommentOwner(comment, user);

        // 자식 댓글이 있는지 검증, 있으면 삭제 못함
        boolean hasChildComments = commentRepository.existsByParent(comment);
        if (hasChildComments) {
            throw new BaseException(ErrorCode.CANNOT_DELETE_COMMENT_WITH_REPLIES);
        }

        commentRepository.delete(comment);
    }

    @Override
    public PostCommentResponse patchComment(
            CustomOAuth2User loginUser,
            Long commentId,
            PatchCommentRequest patchCommentRequest
    ) {
        User user = userValidator.getUser(loginUser);
        Comment comment = findCommentById(commentId);

        // 댓글 작성자 본인인지 검증
        validateCommentOwner(comment, user);

        // 내용 수정
        comment.updateContent(patchCommentRequest.content());

        // 프로필 이미지 조회
        String profileImageUrl = getProfileImageUrl(user.getId());

        return PostCommentResponse.of(comment, loginUser.getUserId(), profileImageUrl);
    }

    // 댓글 ID로 댓글 조회 메서드
    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_COMMENT));
    }

    // 댓글 작성자 본인인지 검증하는 메서드
    private void validateCommentOwner(Comment comment, User user) {
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BaseException(ErrorCode.VALIDATION_ERROR);
        }
    }

    // 프로필 이미지 URL 조회 메서드
    private String getProfileImageUrl(Long userId) {
        return imageRepository.findProfileImagesByUserIds(List.of(userId))
                .stream()
                .findFirst()
                .map(com.momentree.domain.image.entity.Image::getImageUrl)
                .orElse(null);
    }
}

