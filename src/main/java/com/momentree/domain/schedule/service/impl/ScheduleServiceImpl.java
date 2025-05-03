package com.momentree.domain.schedule.service.impl;

import com.momentree.domain.category.entity.Category;
import com.momentree.domain.category.repository.CategoryRepository;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.couple.repository.CoupleRepository;
import com.momentree.domain.schedule.entity.Schedule;
import com.momentree.domain.schedule.repository.ScheduleRepository;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
import com.momentree.domain.schedule.service.ScheduleService;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CoupleRepository coupleRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public void createSchedule(CreateScheduleRequestDto requestDto) {
        
        // Couple과 Category 개발 중이므로 주석 처리
//        Couple couple = coupleRepository.findById(requestDto.coupleId())
//                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_COUPLE));
//
//        Category category = categoryRepository.findById(requestDto.categoryId())
//                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_CATEGORY));

        try {
            // Schedule 객체 생성 및 저장
            // Couple과 Category 개발 중이므로 주석 처리
//            return scheduleRepository.save(Schedule.from(requestDto, couple, category));
            scheduleRepository.save(Schedule.from(requestDto));

        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            // 데이터 무결성 위반이나 잘못된 인자 - 클라이언트 데이터 문제 (400 에러)
            throw new BaseException(ErrorCode.INVALID_SCHEDULE_DATA);

        } catch (Exception e) {
            // 기타 모든 예외 - 서버 문제 (500 에러)
            throw new BaseException(ErrorCode.FAILED_CREATE_SCHEDULE);
        }
    }
}
