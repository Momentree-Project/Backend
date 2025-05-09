package com.momentree.domain.schedule.entity;

import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.schedule.request.PostScheduleRequestDto;
import com.momentree.domain.schedule.request.PatchScheduleRequestDto;
import com.momentree.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "schedule")
@AttributeOverride(name = "id", column = @Column(name = "schedule_id"))
public class Schedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @Column(name = "categoryId")
    private Long categoryId; // ManyToOne 대신 단순 Long 타입 (null 허용을 위해)

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_all_day", nullable = false)
    private Boolean isAllDay;

    @Column(name = "location")
    private String location;

    public static Schedule from(PostScheduleRequestDto dto,
                                Couple couple) {
        return Schedule.builder()
                .couple(couple)
                .categoryId(dto.categoryId())
                .title(dto.title())
                .content(dto.content())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .isAllDay(dto.isAllDay())
                .location(dto.location())
                .build();
    }

    public void update(PatchScheduleRequestDto dto) {
        this.categoryId = dto.categoryId();
        this.title = dto.title();
        this.content = dto.content();
        this.startTime = dto.startTime();
        this.endTime = dto.endTime();
        this.isAllDay = dto.isAllDay();
        this.location = dto.location();
    }
}
