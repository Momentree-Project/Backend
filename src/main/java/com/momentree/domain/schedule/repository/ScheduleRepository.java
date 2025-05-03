package com.momentree.domain.schedule.repository;

import com.momentree.domain.schedule.entity.Schedule;
import com.momentree.domain.schedule.response.ScheduleResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<ScheduleResponseDto> findAllByCoupleId(Long coupleId);
}
