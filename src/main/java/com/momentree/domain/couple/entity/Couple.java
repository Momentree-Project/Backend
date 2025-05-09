package com.momentree.domain.couple.entity;

import com.momentree.domain.couple.dto.request.PatchCoupleStartedDayRequestDto;
import com.momentree.global.constant.Status;
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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Couple from(LocalDate coupleStartedDay) {
        return Couple.builder()
                .coupleStartedDay(coupleStartedDay)
                .status(Status.ACTIVE)
                .build();
    }

    public void patchCoupleStartedDay(PatchCoupleStartedDayRequestDto requestDto) {
        this.coupleStartedDay = requestDto.coupleStartedDay();
    }

    public void inactiveCouple() {
        this.status = Status.INACTIVE;
    }

}
