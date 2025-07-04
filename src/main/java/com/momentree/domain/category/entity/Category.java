package com.momentree.domain.category.entity;

import com.momentree.domain.category.constant.CategoryColor;
import com.momentree.domain.category.constant.CategoryType;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CATEGORY")
@AttributeOverride(name = "id", column = @Column(name = "category_id"))
public class Category extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "memo")
    private String memo;

    @Column(name = "color", nullable = false)
    private CategoryColor categoryColor;

    @Column(name = "type", nullable = false)
    private CategoryType categoryType;

    public void patchScheduleCategory(String name) {
        this.name = name;
    }
}
