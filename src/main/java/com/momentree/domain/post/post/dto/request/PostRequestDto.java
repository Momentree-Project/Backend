package com.momentree.domain.post.post.dto.request;

import com.momentree.global.constant.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record PostRequestDto(
        String content,
        List<MultipartFile> images,
        FileType fileType
) {
    public PostRequestDto {
        if (fileType == null) {
            fileType = FileType.POST;
        }
        if (images == null) {
            images = new ArrayList<>();
        }
    }
}
