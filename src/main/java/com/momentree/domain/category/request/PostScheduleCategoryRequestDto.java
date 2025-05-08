package com.momentree.domain.category.request;

import com.momentree.domain.category.constant.CategoryType;

public record PostScheduleCategoryRequestDto(
        String name,
        String color,
        CategoryType categoryType
) {
}
