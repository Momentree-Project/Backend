package com.momentree.domain.schedule.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.category.repository.CategoryRepository;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.schedule.entity.Schedule;
import com.momentree.domain.schedule.repository.ScheduleRepository;
import com.momentree.domain.schedule.request.CreateScheduleRequestDto;
import com.momentree.domain.schedule.request.UpdateScheduleRequestDto;
import com.momentree.domain.schedule.response.DetailScheduleResponseDto;
import com.momentree.domain.schedule.response.ScheduleResponseDto;
import com.momentree.domain.schedule.service.ScheduleService;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    @Override
    public void createSchedule (
            CreateScheduleRequestDto requestDto,
            CustomOAuth2User loginUser
    ) {
        Couple couple = _validateAndGetCouple(loginUser);

        try {
            // Schedule 객체 생성 및 저장
            scheduleRepository.save(Schedule.from(requestDto, couple));

        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            // 데이터 무결성 위반이나 잘못된 인자 - 클라이언트 데이터 문제 (400 에러)
            throw new BaseException(ErrorCode.INVALID_SCHEDULE_DATA);

        } catch (Exception e) {
            // 기타 모든 예외 - 서버 문제 (500 에러)
            throw new BaseException(ErrorCode.FAILED_CREATE_SCHEDULE);
        }
    }

    @Override
    public List<ScheduleResponseDto> retrieveSchedule (
            CustomOAuth2User loginUser
    ) {
        Couple couple = _validateAndGetCouple(loginUser);

        List<ScheduleResponseDto> list =
                scheduleRepository.findAllByCoupleId(couple.getId());

        if (list.isEmpty()) {
            throw new BaseException(ErrorCode.NOT_FOUND_SCHEDULE);
        }

        return list;
    }

    @Override
    public DetailScheduleResponseDto retrieveDetailSchedule(
            CustomOAuth2User loginUser, Long scheduleId
    ) {
        Couple couple = _validateAndGetCouple(loginUser);

        DetailScheduleResponseDto dto =
                scheduleRepository.findByIdAndCoupleId(scheduleId, couple.getId());

        if (dto == null) {
            throw new BaseException(ErrorCode.NOT_FOUND_SCHEDULE);
        }
        
        return dto;
    }

    @Override
    public void deleteSchedule (
            CustomOAuth2User loginUser,
            Long scheduleId
    ) {
        Couple couple = _validateAndGetCouple(loginUser);

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_SCHEDULE));

        // 스케줄의 커플 ID와 로그인한 사용자의 커플 ID 비교
        if (!couple.getId().equals(schedule.getCouple().getId())) {
            throw new BaseException(ErrorCode.UNEXPECTED_ERROR);
        }

        scheduleRepository.deleteById(scheduleId);
    }

    public DetailScheduleResponseDto updateSchedule(
            CustomOAuth2User loginUser,
            UpdateScheduleRequestDto requestDto,
            Long scheduleId
    ) {
        _validateAndGetCouple(loginUser);

        Schedule schedule = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_SCHEDULE));

        schedule.update(requestDto);

        return DetailScheduleResponseDto.from(schedule);
    }

    private Couple _validateAndGetCouple(CustomOAuth2User loginUser) {
        Couple couple = userRepository.findById(loginUser.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER))
                .getCouple();

        if (couple == null) {
            throw new BaseException(ErrorCode.NOT_FOUND_COUPLE);
        }

        return couple;
    }
}
