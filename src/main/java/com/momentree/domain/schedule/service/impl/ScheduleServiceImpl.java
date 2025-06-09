package com.momentree.domain.schedule.service.impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.notification.strategy.dto.CommentEvent;
import com.momentree.domain.notification.strategy.dto.ScheduleEvent;
import com.momentree.domain.user.entity.User;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.global.validator.UserValidator;
import com.momentree.domain.schedule.entity.Schedule;
import com.momentree.domain.schedule.repository.ScheduleRepository;
import com.momentree.domain.schedule.dto.request.PostScheduleRequestDto;
import com.momentree.domain.schedule.dto.request.PatchScheduleRequestDto;
import com.momentree.domain.schedule.dto.response.GetScheduleResponseDto;
import com.momentree.domain.schedule.dto.response.GetAllScheduleResponseDto;
import com.momentree.domain.schedule.service.ScheduleService;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final ApplicationEventPublisher eventPublisher;
    @Override
    public void postSchedule (
            PostScheduleRequestDto requestDto,
            CustomOAuth2User loginUser
    ) {
        User user = userValidator.getUser(loginUser);
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        try {
            User partner = userRepository.findByCoupleAndIdNot(couple, user.getId())
                    .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

            // Schedule 객체 생성 및 저장
            Schedule schedule = scheduleRepository.save(Schedule.from(requestDto, couple));

            eventPublisher.publishEvent(new ScheduleEvent(
                    partner,
                    user,
                    schedule
            ));

        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            // 데이터 무결성 위반이나 잘못된 인자 - 클라이언트 데이터 문제 (400 에러)
            throw new BaseException(ErrorCode.INVALID_SCHEDULE_DATA);

        } catch (Exception e) {
            // 기타 모든 예외 - 서버 문제 (500 에러)
            throw new BaseException(ErrorCode.FAILED_CREATE_SCHEDULE);
        }
    }

    @Override
    public GetScheduleResponseDto getSchedule(
            CustomOAuth2User loginUser, Long scheduleId
    ) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        Schedule schedule = scheduleRepository.findByIdAndCoupleId(scheduleId, couple.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_SCHEDULE));

        return GetScheduleResponseDto.from(schedule);
    }

    @Override
    public List<GetAllScheduleResponseDto> getAllSchedules(
            CustomOAuth2User loginUser
    ) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        List<Schedule> schedules = scheduleRepository.findAllByCoupleId(couple.getId());

        if (schedules.isEmpty()) {
            throw new BaseException(ErrorCode.NOT_FOUND_SCHEDULE);
        }

        return schedules.stream()
                .map(GetAllScheduleResponseDto::from)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteSchedule (
            CustomOAuth2User loginUser,
            Long scheduleId
    ) {
        Couple couple = userValidator.validateAndGetCouple(loginUser);

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_SCHEDULE));

        // 스케줄의 커플 ID와 로그인한 사용자의 커플 ID 비교
        if (!couple.getId().equals(schedule.getCouple().getId())) {
            throw new BaseException(ErrorCode.UNEXPECTED_ERROR);
        }

        scheduleRepository.deleteById(scheduleId);
    }

    public GetScheduleResponseDto patchSchedule(
            CustomOAuth2User loginUser,
            PatchScheduleRequestDto requestDto,
            Long scheduleId
    ) {
        userValidator.validateAndGetCouple(loginUser);

        Schedule schedule = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_SCHEDULE));

        schedule.update(requestDto);

        return GetScheduleResponseDto.from(schedule);
    }
}