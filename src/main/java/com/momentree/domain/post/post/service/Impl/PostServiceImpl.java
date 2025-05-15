package com.momentree.domain.post.post.service.Impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.post.post.constant.PostStatus;
import com.momentree.domain.post.post.dto.response.PatchPostRequestDto;
import com.momentree.domain.user.entity.User;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import com.momentree.global.validator.UserValidator;
import com.momentree.domain.post.post.dto.request.PostRequestDto;
import com.momentree.domain.post.post.dto.response.PostResponseDto;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.post.post.repository.PostRepository;
import com.momentree.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserValidator userValidator;
    @Override
    public PostResponseDto createPost(
            CustomOAuth2User loginUser,
            PostRequestDto requestDto
    ) {
        User user = userValidator.getUser(loginUser);
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        Post post = Post.builder()
                .content(requestDto.content())
                .user(user)
                .couple(couple)
                .status(PostStatus.PUBLISHED)
                .build();

        postRepository.save(post);

        return PostResponseDto.from(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts(
            CustomOAuth2User loginUser
    ) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        List<Post> posts = postRepository.findAllByCoupleId(couple.getId());

        return posts.stream()
                .map(PostResponseDto :: from)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto patchPost(
            CustomOAuth2User loginUser,
            PatchPostRequestDto requestDto
    ) {
        userValidator.validateAndGetCouple(loginUser);

        Post post = postRepository.findById(requestDto.postId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        if (post.getStatus() == PostStatus.PUBLISHED) {
            post.patchPost(requestDto.content());
        } else {
            throw new BaseException(ErrorCode.POST_STATUS_NOT_PUBLISHED);
        }
        return PostResponseDto.from(post);
    }
}
