package com.momentree.domain.schedule.repository;

import com.momentree.domain.schedule.entity.Schedule;
import com.momentree.domain.schedule.response.GetScheduleResponseDto;
import com.momentree.domain.schedule.response.GetAllScheduleResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<GetAllScheduleResponseDto> findAllByCoupleId(Long coupleId);

    GetScheduleResponseDto findByIdAndCoupleId(Long scheduleId, Long coupleId);
}
