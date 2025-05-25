package com.momentree.domain.user;

import com.momentree.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserCodeManager {
    private final UserRepository userRepository;

    public String generateUserCode() {
        String userCode;
        do {
            userCode = UUID.randomUUID().toString()
                    .replace("-", "")
                    .substring(0, 8);
        } while (userRepository.existsByUserCode(userCode));

        return userCode;
    }
}
