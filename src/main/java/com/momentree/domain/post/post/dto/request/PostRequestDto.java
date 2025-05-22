package com.momentree.domain.post.post.dto.request;

import com.momentree.global.constant.FileType;
import org.springframework.web.multipart.MultipartFile;

public record PostRequestDto(
        String content,
        MultipartFile image,
        FileType fileType
) {
    public PostRequestDto {
        if (fileType == null) {
            fileType = FileType.POST;
        }
    }
}
