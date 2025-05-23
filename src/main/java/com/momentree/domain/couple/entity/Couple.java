package com.momentree.domain.couple.entity;

import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COUPLE")
@AttributeOverride(name = "id", column = @Column(name = "couple_id"))
public class Couple extends BaseEntity {

    @Column(name = "couple_started_day", nullable = false)
    private LocalDate coupleStartedDay;

    @Column(name = "couple_nickname")
    private String coupleNickname;

}
