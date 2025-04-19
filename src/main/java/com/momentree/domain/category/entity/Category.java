package com.momentree.domain.category.entity;

import com.momentree.domain.category.constant.CategoryColor;
import com.momentree.domain.category.constant.CategoryType;
import com.momentree.domain.datemarker.entity.DateMarkerCategory;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CATEGORY")
@AuditOverride(forClass = BaseEntity.class)
@AttributeOverride(name = "id", column = @Column(name = "category_id"))
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "memo")
    private String memo;

    @Column(name = "color", nullable = false)
    private CategoryColor categoryColor;

    @Column(name = "type", nullable = false)
    private CategoryType categoryType;

}
