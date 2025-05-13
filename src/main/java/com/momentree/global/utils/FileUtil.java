package com.momentree.global.utils;

import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FileUtil {
    public static final List<String> ALLOWED_EXTENSIONS_IMAGE = Arrays.asList("jpg", "jpeg", "png", "gif");

    public void validateFile(String fileName) {
        String fileExtension = getFileExtension(fileName);
        if (!ALLOWED_EXTENSIONS_IMAGE.contains(fileExtension)) {
            throw new BaseException(ErrorCode.INVALID_FILE_EXTENSION);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1).toLowerCase();
    }
}
