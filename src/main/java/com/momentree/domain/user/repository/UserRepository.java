package com.momentree.domain.user.repository;

import com.momentree.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderIdAndProvider(String providerId, String provider);
    boolean existsByUserCode(String userCode);
    Optional<User> findByUserCode(String userCode);
}
