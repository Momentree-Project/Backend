package com.momentree.domain.image.repository;

import com.momentree.domain.image.entity.Image;
import com.momentree.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUserAndPostIsNull(User user);
    List<Image> findByPostIdIn(Collection<Long> postIds);
}
