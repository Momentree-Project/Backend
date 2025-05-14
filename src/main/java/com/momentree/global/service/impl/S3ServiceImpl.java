package com.momentree.global.service.impl;

import com.momentree.domain.image.entity.Image;
import com.momentree.domain.image.repository.ImageRepository;
import com.momentree.domain.user.entity.User;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import com.momentree.global.service.S3Service;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final FileUtil fileUtil;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value(("${cloud.aws.s3.bucket}"))
    private String bucketName;

    @Transactional
    @Override
    public String uploadProfileImage(MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        // 기존 프로필 이미지가 db에 있으면 삭제
        Optional<Image> existingProfileImage = imageRepository.findByUserAndPostIsNull(user);
        if (existingProfileImage.isPresent()) {
            // db에서 삭제
            imageRepository.delete(existingProfileImage.get());

            // s3 이미지 삭제
            String existingFileName = extractProfileFileName(existingProfileImage.get().getImageUrl());
            deleteS3Image(existingFileName);
        }

        String fileName = generateS3ProfileFileName(file);
        // 파일 확장자 검사
        fileUtil.validateFile(fileName);
        // s3에 업로드
        uploadS3Image(file, fileName);

        // db 저장
        String fileUrl = generateS3FileUrl(fileName);
        Image image = Image.of(fileUrl, user);
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
            String fileName = extractProfileFileName(existingProfileImage.get().getImageUrl());
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

    private String generateS3ProfileFileName(MultipartFile file) {
        return "profile/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
    }

    private String generateS3FileUrl(String fileName) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }

    private String extractProfileFileName(String fileUrl) {
        return fileUrl.replace("https://" + bucketName + ".s3." + region + ".amazonaws.com/", "");
    }

}
