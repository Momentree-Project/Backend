package com.momentree.domain.category.dto.request;

import com.momentree.domain.category.constant.CategoryType;

public record PatchScheduleCategoryRequestDto (
        Long categoryId,
        String name,
        CategoryType categoryType
) {
}
