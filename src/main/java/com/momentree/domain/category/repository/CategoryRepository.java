package com.momentree.domain.category.repository;

import com.momentree.domain.category.constant.CategoryType;
import com.momentree.domain.category.entity.Category;
import com.momentree.domain.couple.entity.Couple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByCoupleAndCategoryType(Couple couple, CategoryType categoryType);
}
