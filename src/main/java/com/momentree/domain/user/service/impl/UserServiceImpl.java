package com.momentree.domain.user.service.impl;

import com.momentree.domain.user.entity.User;
import com.momentree.domain.user.repository.UserRepository;
import com.momentree.domain.user.dto.request.UserAdditionalInfoRequestDto;
import com.momentree.domain.user.dto.response.UserAdditionalInfoResponseDto;
import com.momentree.domain.user.service.UserService;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserAdditionalInfoResponseDto patchUserAdditionalInfo(Long userId, UserAdditionalInfoRequestDto requestDto) {
        User findedUser = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        findedUser.updateUserAdditionalInfo(requestDto);
        User updatedUser = userRepository.save(findedUser);
        return UserAdditionalInfoResponseDto.from(updatedUser);
    }
}
