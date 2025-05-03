package com.momentree.domain.schedule.entity;

import com.momentree.domain.category.entity.Category;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

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

    // Couple과 Category 개발 중이므로 주석 처리, 아래에 임시 코드 추가
    public static Schedule from(CreateScheduleRequestDto dto,
                                Couple couple,
                                Category category) {
        return Schedule.builder()
                .couple(couple)
                .category(category)
                .title(dto.title())
                .content(dto.content())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .isAllDay(dto.isAllDay())
                .location(dto.location())
                .build();
    }

    // Couple과 Category 개발 중이므로 임시로 사용
//    public static Schedule from(CreateScheduleRequestDto dto) {
//        return Schedule.builder()
//                .title(dto.title())
//                .content(dto.content())
//                .startTime(dto.startTime())
//                .endTime(dto.endTime())
//                .isAllDay(dto.isAllDay())
//                .location(dto.location())
//                .build();
//    }
}
