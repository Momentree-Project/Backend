package com.momentree.domain.post.post.service.Impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.image.entity.Image;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
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
    public PostResponseDto createPost(CustomOAuth2User loginUser, PostRequestDto requestDto) {
        User user = userValidator.getUser(loginUser);
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        Post post = Post.of(
                requestDto.content(),
                user,
                couple,
                PostStatus.PUBLISHED
        );

        postRepository.save(post);

        List<String> imageUrls = new ArrayList<>();
        List<Long> imageIds = new ArrayList<>(); // 이미지 ID 목록 추가

        if (requestDto.images() != null && !requestDto.images().isEmpty()) {
            for (MultipartFile image : requestDto.images()) {
                try {
                    String imageUrl = s3Service.uploadImage(
                            image,
                            requestDto.fileType(),
                            user,
                            post
                    );
                    imageUrls.add(imageUrl);

                    // 업로드된 이미지의 ID 가져오기
                    Image uploadedImage = imageRepository.findByImageUrlAndPost(imageUrl, post)
                            .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_IMAGE));
                    imageIds.add(uploadedImage.getId());
                } catch (IOException e) {
                    throw new BaseException(ErrorCode.FAILED_TO_CONNECT_SERVER);
                }
            }
        }

        return PostResponseDto.of(post, loginUser.getUserId(), imageUrls, imageIds);
    }


    @Override
    public List<PostResponseDto> getAllPosts(CustomOAuth2User loginUser) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);
        List<Post> posts = postRepository.findAllByCoupleId(couple.getId());

        // 모든 게시글 ID 목록 추출
        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .collect(Collectors.toList());

        // 각 게시글의 이미지 URL과 ID 맵 생성
        Map<Long, List<String>> postImageUrlsMap = new HashMap<>();
        // 이미지 ID 맵 추가
        Map<Long, List<Long>> postImageIdsMap = new HashMap<>();

        // 모든 게시글의 이미지 조회
        List<Image> allImages = imageRepository.findByPostIdIn(postIds);

        // 게시글별로 이미지 URL과 ID 그룹화
        for (Image image : allImages) {
            Long postId = image.getPost().getId();

            // URL 맵에 추가
            postImageUrlsMap.computeIfAbsent(postId, k -> new ArrayList<>())
                    .add(image.getImageUrl());

            // ID 맵에 추가
            postImageIdsMap.computeIfAbsent(postId, k -> new ArrayList<>())
                    .add(image.getId());
        }

        return posts.stream()
                .map(post -> {
                    List<String> imageUrls = postImageUrlsMap.getOrDefault(post.getId(), Collections.emptyList());
                    List<Long> imageIds = postImageIdsMap.getOrDefault(post.getId(), Collections.emptyList());
                    return PostResponseDto.of(post, loginUser.getUserId(), imageUrls, imageIds);
                })
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto patchPost(
            CustomOAuth2User loginUser,
            PatchPostRequestDto requestDto
    ) {
        User user = userValidator.getUser(loginUser);
        userValidator.validateAndGetCouple(loginUser);

        Post post = postRepository.findById(requestDto.postId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        if (post.getStatus() != PostStatus.PUBLISHED) {
            throw new BaseException(ErrorCode.POST_STATUS_NOT_PUBLISHED);
        }

        // 내용 수정
        post.patchPost(requestDto.content());

        // 1. 삭제 요청된 이미지만 삭제
        if (requestDto.deleteImageIds() != null && !requestDto.deleteImageIds().isEmpty()) {
            for (Long imageId : requestDto.deleteImageIds()) {
                Image image = imageRepository.findById(imageId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_IMAGE));

                // 해당 이미지가 현재 게시글의 것인지 확인
                if (image.getPost() != null && image.getPost().getId().equals(post.getId())) {
                    // 단일 이미지 삭제
                    s3Service.deleteImage(image);
                }
            }
        }

        // 2. 새 이미지가 있으면 추가 (기존 이미지 유지)
        if (requestDto.images() != null && !requestDto.images().isEmpty()) {
            for (MultipartFile file : requestDto.images()) {
                if (file != null && !file.isEmpty()) {
                    try {
                        s3Service.uploadImage(
                                file,
                                requestDto.fileType(),
                                user,
                                post
                        );
                    } catch (IOException e) {
                        throw new BaseException(ErrorCode.FAILED_TO_CONNECT_SERVER);
                    }
                }
            }
        }

        // 3. 현재 게시글의 모든 이미지 URL 조회
        List<Image> images = imageRepository.findByPost(post);
        List<String> imageUrls = images.stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
        List<Long> imageIds = images.stream()
                .map(Image::getId)
                .collect(Collectors.toList());

        // 응답 생성 (이미지 URL 목록 포함)
        return PostResponseDto.of(post, loginUser.getUserId(), imageUrls, imageIds);
    }

    @Override
    public void deletePost(
            CustomOAuth2User loginUser,
            Long postId
    ) {
        User user = userValidator.getUser(loginUser);
        userValidator.validateAndGetCouple(loginUser);

        Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_POST));

        // 게시글 이미지 삭제
        s3Service.deletePostImage(user, post);
        
        // 게시글 삭제
        postRepository.delete(post);
    }
}
