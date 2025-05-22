package com.momentree.global.strategy;

import org.springframework.web.multipart.MultipartFile;

public interface FileNameStrategy {
    String generateFileName(MultipartFile file);
}
