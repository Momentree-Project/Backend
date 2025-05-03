package com.momentree.domain.schedule.entity;

import com.momentree.domain.schedule.entity.Schedule;
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
@Table(name = "schedule_reminder")
@AttributeOverride(name = "id", column = @Column(name = "schedule_reminder_id"))
public class ScheduleReminder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "notify_time")
    private LocalDateTime notifyTime;

}
