package com.momentree.global.strategy;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component("postFileNameStrategy")
public class PostFileNameStrategy implements FileNameStrategy {
    @Override
    public String generateFileName(MultipartFile file) {
        return "post/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
    }
}
