package com.momentree.domain.schedule.repository;

import com.momentree.domain.schedule.entity.Schedule;
import com.momentree.domain.schedule.response.GetScheduleResponseDto;
import com.momentree.domain.schedule.response.GetAllScheduleResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByCoupleId(Long coupleId);

    Optional<Schedule> findByIdAndCoupleId(Long scheduleId, Long coupleId);
}
