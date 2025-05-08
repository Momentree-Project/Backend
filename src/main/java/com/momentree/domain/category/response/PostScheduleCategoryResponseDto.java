package com.momentree.domain.category.response;

import com.momentree.domain.category.entity.Category;

public record PostScheduleCategoryResponseDto(
        Long id,
        String name,
        String color,
        String categoryType

) {
    public static PostScheduleCategoryResponseDto from(
            Category category
    ) {
        return new PostScheduleCategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getCategoryColor().toString(),
                category.getCategoryType().toString()
        );
    }
}
