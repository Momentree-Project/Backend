package com.momentree.domain.datemarker.entity;

import com.momentree.domain.datemarker.constant.CoupleRating;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DATEMARKER")
@AuditOverride(forClass = BaseEntity.class)
@AttributeOverride(name = "id", column = @Column(name = "datemarker_id"))
public class DateMarker extends BaseEntity {

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "memo")
    private String memo;

    @Column(name = "couple_rating")
    private CoupleRating coupleRating;

    @OneToMany(mappedBy = "dateMarker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DateMarkerCategory> categories = new ArrayList<>();

}
