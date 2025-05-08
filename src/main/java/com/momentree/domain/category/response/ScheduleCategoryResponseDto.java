package com.momentree.domain.category.response;

import com.momentree.domain.category.entity.Category;

public record ScheduleCategoryResponseDto(
        Long id,
        String name,
        String color,
        String categoryType

) {
    public static ScheduleCategoryResponseDto from(
            Category category
    ) {
        return new ScheduleCategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getCategoryColor().toString(),
                category.getCategoryType().toString()
        );
    }
}
