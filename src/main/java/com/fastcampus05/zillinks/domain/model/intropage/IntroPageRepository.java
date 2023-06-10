package com.fastcampus05.zillinks.domain.model.intropage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntroPageRepository extends JpaRepository<IntroPage, Long> {

    Optional<IntroPage> findByUserId(Long userId);
}
