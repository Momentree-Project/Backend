package com.momentree.global.service;

import com.momentree.domain.image.entity.Image;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.user.entity.User;
import com.momentree.global.constant.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    // 프로필 이미지용
    String uploadImage(MultipartFile file, FileType strategyType, Long userId) throws IOException;
    // 게시글 이미지용
    String uploadImage(MultipartFile file, FileType strategyType, User user, Post post) throws IOException;
    void deleteProfileImage(Long userId);
    void deletePostImage(User user, Post post);
    void deleteImage(Image image);
}
