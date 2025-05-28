package com.momentree.global.service.impl;

import com.momentree.domain.image.entity.Image;
import com.momentree.domain.image.repository.ImageRepository;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.entity.User;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.global.constant.FileType;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import com.momentree.global.service.S3Service;
import com.momentree.global.strategy.FileNameStrategy;
import com.momentree.global.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final FileUtil fileUtil;

    private final Map<String, FileNameStrategy> fileNameStrategyMap;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value(("${cloud.aws.s3.bucket}"))
    private String bucketName;

    // 프로필 이미지 업로드
    @Transactional
    @Override
    public String uploadImage(MultipartFile file, FileType strategyType, Long userId) throws IOException {
        // PROFILE 타입 검증
        if (FileType.PROFILE != strategyType) {
            throw new BaseException(ErrorCode.INVALID_FILE_TYPE);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        // 공통 로직 호출
        String fileUrl = generateAndUploadImage(file, strategyType);

        // 프로필 이미지 처리 (기존 이미지 삭제)
        Optional<Image> existingProfileImage = imageRepository.findByUserAndPostIsNull(user);
        if (existingProfileImage.isPresent()) {
            imageRepository.delete(existingProfileImage.get());
            String existingFileName = extractFileName(existingProfileImage.get().getImageUrl());
            deleteS3Image(existingFileName);
        }

        // DB 저장
        Image image = Image.of(fileUrl, user);
        imageRepository.save(image);

        return fileUrl;
    }

    // 게시글 이미지 업로드
    @Transactional
    @Override
    public String uploadImage(MultipartFile file, FileType strategyType, Post post) throws IOException {
        // POST 타입 검증
        if (FileType.POST != strategyType) {
            throw new BaseException(ErrorCode.INVALID_FILE_TYPE);
        }

        // 공통 로직 호출
        String fileUrl = generateAndUploadImage(file, strategyType);

        // DB 저장
        Image image = Image.of(fileUrl, post);
        imageRepository.save(image);

        return fileUrl;
    }

    @Transactional
    @Override
    public void deleteProfileImage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        // 기존 프로필 이미지가 있는지 확인
        Optional<Image> existingProfileImage = imageRepository.findByUserAndPostIsNull(user);

        if (existingProfileImage.isPresent()) {
            // db에서 삭제
            imageRepository.delete(existingProfileImage.get());

            // s3 이미지 삭제
            String fileName = extractFileName(existingProfileImage.get().getImageUrl());
            deleteS3Image(fileName);
        }
    }

    @Transactional
    @Override
    public void deletePostImage(User user, Post post) {
        // 게시글에 연결된 모든 이미지 찾기
        List<Image> postImages = imageRepository.findByPost(post);

        // 각 이미지 삭제
        for (Image image : postImages) {
            // DB에서 삭제
            imageRepository.delete(image);

            // S3에서 삭제
            String fileName = extractFileName(image.getImageUrl());
            deleteS3Image(fileName);
        }
    }


    private void uploadS3Image(MultipartFile file, String fileName) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }

    private void deleteS3Image(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    // 기존 프로필 이미지 파일 이름 생성 메서드
//    private String generateS3ProfileFileName(MultipartFile file) {
//        return "profile/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
//    }

    private String generateS3FileUrl(String fileName) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }

    private String extractFileName(String fileUrl) {
        return fileUrl.replace("https://" + bucketName + ".s3." + region + ".amazonaws.com/", "");
    }

    private String generateAndUploadImage(MultipartFile file, FileType strategyType) throws IOException {
        // 전략 선택
        FileNameStrategy strategy = fileNameStrategyMap.get(strategyType.getStrategyName());
        if (strategy == null) {
            throw new BaseException(ErrorCode.NOT_FOUND_STRATEGY_NAME);
        }

        // 파일명 생성
        String fileName = strategy.generateFileName(file);

        // 파일 확장자 검사
        fileUtil.validateFile(fileName);

        // s3에 업로드
        uploadS3Image(file, fileName);

        // URL 생성
        return generateS3FileUrl(fileName);
    }

    @Transactional
    public void deleteImage(Image image) {
        // DB에서 삭제
        imageRepository.delete(image);

        // S3에서 삭제
        String fileName = extractFileName(image.getImageUrl());
        deleteS3Image(fileName);
    }
}
