package com.fastcampus05.zillinks.domain.model.intropage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IntroPageRepository extends JpaRepository<IntroPage, Long> {

    Optional<IntroPage> findByUserId(Long userId);

    @Query("select i from IntroPage i where i.siteInfo.subDomain = :subDomain")
    Optional<IntroPage> findByDomain(String subDomain);
}
