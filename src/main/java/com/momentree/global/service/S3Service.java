package com.momentree.global.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    String uploadProfileImage(MultipartFile file, Long userId) throws IOException;

    void deleteProfileImage(Long userId);
}
