package com.momentree.domain.category.dto.request;

import com.momentree.domain.category.constant.CategoryColor;
import com.momentree.domain.category.constant.CategoryType;

public record PostScheduleCategoryRequestDto(
        String name,
        CategoryColor color,
        CategoryType categoryType
) {
}
