package com.momentree.domain.user.repository;

import com.momentree.domain.couple.entity.Couple;
import com.momentree.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCoupleAndIdNot(Couple couple, Long userId);
    Optional<User> findByProviderIdAndProvider(String providerId, String provider);
    boolean existsByUserCode(String userCode);
    Optional<User> findByUserCode(String userCode);
    @Query("SELECT u.email FROM User u " +
            "WHERE u.couple = :couple AND u.id <> :myUserId")
    Optional<String> findPartnerEmailByCoupleAndUserId(@Param("couple") Couple couple, @Param("myUserId") Long myUserId);
    List<User> findByCouple(Couple couple);
}
