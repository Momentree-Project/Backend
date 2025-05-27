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
        List<Long> imageIds = new ArrayList<>();

        if (requestDto.images() != null && !requestDto.images().isEmpty()) {
            for (MultipartFile image : requestDto.images()) {
                try {
                    String imageUrl = s3Service.uploadImage(
                            image,
                            requestDto.fileType(),
                            post
                    );
                    imageUrls.add(imageUrl);

                    Image uploadedImage = imageRepository.findByImageUrlAndPost(imageUrl, post)
                            .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_IMAGE));
                    imageIds.add(uploadedImage.getId());
                } catch (IOException e) {
                    throw new BaseException(ErrorCode.FAILED_TO_CONNECT_SERVER);
                }
            }
        }

        String profileImageUrl = getProfileImageUrl(user.getId());
        return PostResponseDto.of(post, loginUser.getUserId(), imageUrls, imageIds, profileImageUrl);
    }

    @Override
    public List<PostResponseDto> getAllPosts(CustomOAuth2User loginUser) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);
        List<Post> posts = postRepository.findAllByCoupleId(couple.getId());

        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .collect(Collectors.toList());

        List<Long> userIds = posts.stream()
                .map(post -> post.getUser().getId())
                .distinct()
                .collect(Collectors.toList());

        Map<Long, List<String>> postImageUrlsMap = new HashMap<>();
        Map<Long, List<Long>> postImageIdsMap = new HashMap<>();

        List<Image> allImages = imageRepository.findByPostIdIn(postIds);

        for (Image image : allImages) {
            Long postId = image.getPost().getId();

            postImageUrlsMap.computeIfAbsent(postId, k -> new ArrayList<>())
                    .add(image.getImageUrl());

            postImageIdsMap.computeIfAbsent(postId, k -> new ArrayList<>())
                    .add(image.getId());
        }

        Map<Long, String> userProfileImageMap = new HashMap<>();
        List<Image> profileImages = imageRepository.findProfileImagesByUserIds(userIds);

        for (Image profileImage : profileImages) {
            userProfileImageMap.put(profileImage.getUser().getId(), profileImage.getImageUrl());
        }

        return posts.stream()
                .map(post -> {
                    List<String> imageUrls = postImageUrlsMap.getOrDefault(post.getId(), Collections.emptyList());
                    List<Long> imageIds = postImageIdsMap.getOrDefault(post.getId(), Collections.emptyList());
                    String profileImageUrl = userProfileImageMap.get(post.getUser().getId());

                    return PostResponseDto.of(post, loginUser.getUserId(), imageUrls, imageIds, profileImageUrl);
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

        post.patchPost(requestDto.content());

        if (requestDto.deleteImageIds() != null && !requestDto.deleteImageIds().isEmpty()) {
            for (Long imageId : requestDto.deleteImageIds()) {
                Image image = imageRepository.findById(imageId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_IMAGE));

                if (image.getPost() != null && image.getPost().getId().equals(post.getId())) {
                    s3Service.deleteImage(image);
                }
            }
        }

        if (requestDto.images() != null && !requestDto.images().isEmpty()) {
            for (MultipartFile file : requestDto.images()) {
                if (file != null && !file.isEmpty()) {
                    try {
                        s3Service.uploadImage(
                                file,
                                requestDto.fileType(),
                                post
                        );
                    } catch (IOException e) {
                        throw new BaseException(ErrorCode.FAILED_TO_CONNECT_SERVER);
                    }
                }
            }
        }

        List<Image> images = imageRepository.findByPost(post);
        List<String> imageUrls = images.stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
        List<Long> imageIds = images.stream()
                .map(Image::getId)
                .collect(Collectors.toList());

        String profileImageUrl = getProfileImageUrl(user.getId());
        return PostResponseDto.of(post, loginUser.getUserId(), imageUrls, imageIds, profileImageUrl);
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

    // 프로필 이미지 URL을 가져오는 메서드
    private String getProfileImageUrl(Long userId) {
        return imageRepository.findProfileImagesByUserIds(List.of(userId))
                .stream()
                .findFirst()
                .map(Image::getImageUrl)
                .orElse(null);
    }
}
