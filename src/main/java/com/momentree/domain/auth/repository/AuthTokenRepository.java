package com.momentree.domain.auth.repository;

import com.momentree.domain.auth.entity.AuthToken;
import com.momentree.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findAuthTokenByUser(User user);

    @Query("SELECT a FROM AuthToken a JOIN FETCH a.user WHERE a.refreshToken = :refreshToken")
    Optional<AuthToken> findByTokenWithUser(@Param("refreshToken") String refreshToken);
}
