package com.fastcampus05.zillinks.domain.model.intropage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteInfoRepository extends JpaRepository<SiteInfo, Long> {

    Optional<SiteInfo> findBySubDomain(String subDomain);
}
