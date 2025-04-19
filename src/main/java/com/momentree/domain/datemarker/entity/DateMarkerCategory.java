package com.momentree.domain.datemarker.entity;


import com.momentree.domain.category.entity.Category;
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
@Table(name = "DATEMARKER_CATEGORY")
@AttributeOverride(name = "id", column = @Column(name = "datemarker_category_id"))
public class DateMarkerCategory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datemarker_id", nullable = false)
    private DateMarker dateMarker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
