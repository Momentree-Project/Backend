package com.momentree.domain.category.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.category.constant.CategoryColor;
import com.momentree.domain.category.constant.CategoryType;
import com.momentree.domain.category.entity.Category;
import com.momentree.domain.category.repository.CategoryRepository;
import com.momentree.domain.category.request.PostScheduleCategoryRequestDto;
import com.momentree.domain.category.response.PostScheduleCategoryResponseDto;
import com.momentree.domain.category.service.CategoryService;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.couple.validator.CoupleValidator;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CoupleValidator coupleValidator;
    @Override
    public PostScheduleCategoryResponseDto postScheduleCategory(
            CustomOAuth2User loginUser,
            PostScheduleCategoryRequestDto requestDto
    ) {
        Couple couple = coupleValidator.validateAndGetCouple(loginUser);

        // String을 CategoryColor ENUM으로 변환
        CategoryColor categoryColor = CategoryColor.valueOf(requestDto.color());

        Category category = categoryRepository.save(Category.builder()
                .couple(couple)
                .name(requestDto.name())
                .categoryColor(categoryColor)
                .categoryType(requestDto.categoryType())
                .build());

        return PostScheduleCategoryResponseDto.from(category);
    }

    @Override
    public List<PostScheduleCategoryResponseDto> getScheduleCategory(CustomOAuth2User loginUser) {
        Couple couple = coupleValidator.validateAndGetCouple(loginUser);

        List<Category> categories = categoryRepository.findAllByCoupleAndCategoryType(couple, CategoryType.SCHEDULE);

        return categories.stream()
                .map(PostScheduleCategoryResponseDto::from)
                .collect(Collectors.toList());
    }
}
