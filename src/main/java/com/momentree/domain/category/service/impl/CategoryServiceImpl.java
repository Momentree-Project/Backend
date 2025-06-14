package com.momentree.domain.category.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.category.constant.CategoryType;
import com.momentree.domain.category.entity.Category;
import com.momentree.domain.category.repository.CategoryRepository;
import com.momentree.domain.category.dto.request.PatchScheduleCategoryRequestDto;
import com.momentree.domain.category.dto.request.PostScheduleCategoryRequestDto;
import com.momentree.domain.category.dto.response.ScheduleCategoryResponseDto;
import com.momentree.domain.category.service.CategoryService;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.global.validator.UserValidator;
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
    private final UserValidator userValidator;
    @Override
    public ScheduleCategoryResponseDto postScheduleCategory(
            CustomOAuth2User loginUser,
            PostScheduleCategoryRequestDto requestDto
    ) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        Category category = categoryRepository.save(Category.builder()
                .couple(couple)
                .name(requestDto.name())
                .categoryColor(requestDto.color())
                .categoryType(requestDto.categoryType())
                .build());

        return ScheduleCategoryResponseDto.from(category);
    }

    @Override
    public List<ScheduleCategoryResponseDto> getScheduleCategory(CustomOAuth2User loginUser) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        List<Category> categories = categoryRepository.findAllByCoupleAndCategoryType(couple, CategoryType.SCHEDULE);

        return categories.stream()
                .map(ScheduleCategoryResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleCategoryResponseDto patchScheduleCategory(
            CustomOAuth2User loginUser,
            PatchScheduleCategoryRequestDto requestDto
    ) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        Category category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_CATEGORY));

        // 카테고리가 해당 커플의 것인지 확인
        if (!category.getCouple().equals(couple)) {
            throw new BaseException(ErrorCode.NOT_FOUND_CATEGORY);
        }

        // 업데이트
        category.patchScheduleCategory(requestDto.name());

        return ScheduleCategoryResponseDto.from(category);
    }

    @Override
    public void deleteScheduleCategory(
            CustomOAuth2User loginUser,
            Long categoryId
    ) {
        userValidator.validateAndGetCouple(loginUser);

        categoryRepository.delete(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_CATEGORY)));
    }
}
