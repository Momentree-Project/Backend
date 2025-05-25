package com.momentree.domain.post.like.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.like.dto.response.GetPostLikesResponse;
import com.momentree.domain.post.like.entity.Likes;
import com.momentree.domain.post.like.repository.LikeRepository;
import com.momentree.domain.post.like.service.LikeService;
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
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserValidator userValidator;
    @Override
    public void likePost(
            CustomOAuth2User loginUser,
            Long postId
    ) {
        User user = userValidator.getUser(loginUser);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        if (likeRepository.existsByUserAndPost(user, post)) {
            // 좋아요 취소
            likeRepository.deleteByUserAndPost(user, post);
            post.decreaseLikeCount();
        } else {
            // 좋아요 추가
            Likes likes = Likes.of(user, post);
            likeRepository.save(likes);
            post.increaseLikeCount();
        }

        postRepository.save(post);
    }

    @Override
    public GetPostLikesResponse getPostLikesCount(
            CustomOAuth2User loginUser,
            Long postId
    ) {
        User user = userValidator.getUser(loginUser);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        boolean isLikedByCurrentUser = likeRepository.existsByUserAndPost(user, post);

        return GetPostLikesResponse.of(post, isLikedByCurrentUser);
    }
}
