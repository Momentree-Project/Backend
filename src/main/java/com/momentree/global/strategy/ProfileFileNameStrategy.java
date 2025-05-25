package com.momentree.global.strategy;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component("profileFileNameStrategy")
public class ProfileFileNameStrategy implements FileNameStrategy {
    @Override
    public String generateFileName(MultipartFile file) {
        return "profile/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
    }
}
