package com.momentree.domain.datemarker.entity;

import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.datemarker.constant.CoupleRating;
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
    @Enumerated(EnumType.STRING)
    private CoupleRating coupleRating;

    @ManyToOne
    @JoinColumn(name = "couple_id", nullable = false)
    private Couple couple;

}
