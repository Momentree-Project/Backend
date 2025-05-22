package com.momentree.domain.post.post.service.Impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.image.repository.ImageRepository;
import com.momentree.domain.post.post.constant.PostStatus;
import com.momentree.domain.post.post.dto.response.PatchPostRequestDto;
import com.momentree.domain.user.entity.User;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import com.momentree.global.service.S3Service;
import com.momentree.global.validator.UserValidator;
import com.momentree.domain.post.post.dto.request.PostRequestDto;
import com.momentree.domain.post.post.dto.response.PostResponseDto;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.post.post.repository.PostRepository;
import com.momentree.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserValidator userValidator;
    private final S3Service s3Service;
    @Override
    public PostResponseDto createPost(
            CustomOAuth2User loginUser,
            PostRequestDto requestDto
    ) {
        User user = userValidator.getUser(loginUser);
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        Post post = Post.of(
                    requestDto.content(),
                    user,
                    couple,
                    PostStatus.PUBLISHED
                );

        postRepository.save(post);

        if (requestDto.image() != null) {
            try {
                s3Service.uploadImage(
                        requestDto.image(),
                        requestDto.fileType(),
                        user,
                        post
                );
            } catch (IOException e) {
                throw new BaseException(ErrorCode.FAILED_TO_CONNECT_SERVER);
            }
        }

        return PostResponseDto.of(post, loginUser.getUserId());
    }

    @Override
    public List<PostResponseDto> getAllPosts(
            CustomOAuth2User loginUser
    ) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);
        List<Post> posts = postRepository.findAllByCoupleId(couple.getId());

        // 모든 게시글 ID 목록 추출
        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .collect(Collectors.toList());

        // 한 번의 쿼리로 모든 게시글의 이미지 조회
        Map<Long, String> postImageMap = imageRepository.findByPostIdIn(postIds).stream()
                .collect(Collectors.toMap(
                        image -> image.getPost().getId(),
                        image -> image.getImageUrl(),
                        (existing, replacement) -> existing  // 중복 시 첫 번째 값 유지
                ));

        return posts.stream()
                .map(post -> {
                    String imageUrl = postImageMap.get(post.getId());
                    return PostResponseDto.of(post, loginUser.getUserId(), imageUrl);
                })
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
        return PostResponseDto.of(post, loginUser.getUserId());
    }

    @Override
    public void deletePost(
            CustomOAuth2User loginUser,
            Long postId
    ) {
        userValidator.validateAndGetCouple(loginUser);

        Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        postRepository.delete(post);
    }
}
