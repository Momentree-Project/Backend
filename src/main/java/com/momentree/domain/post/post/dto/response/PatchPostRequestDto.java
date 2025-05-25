package com.momentree.domain.post.post.dto.response;

import com.momentree.global.constant.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record PatchPostRequestDto(
        Long postId,
        String content,
        List<MultipartFile> images,
        List<Long> deleteImageIds,
        FileType fileType
) {
    public PatchPostRequestDto {
        if (fileType == null) {
            fileType = FileType.POST;
        }
        if (deleteImageIds == null) {
            deleteImageIds = new ArrayList<>();
        }
        if (images == null) {
            images = new ArrayList<>();
        }
    }
}
