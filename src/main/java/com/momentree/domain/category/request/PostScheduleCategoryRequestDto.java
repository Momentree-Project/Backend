package com.momentree.domain.category.request;

import com.momentree.domain.category.constant.CategoryColor;
import com.momentree.domain.category.constant.CategoryType;

public record PostScheduleCategoryRequestDto(
        String name,
        CategoryColor color,
        CategoryType categoryType
) {
}
