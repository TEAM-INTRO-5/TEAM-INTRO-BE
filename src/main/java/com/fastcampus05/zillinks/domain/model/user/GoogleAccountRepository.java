package com.fastcampus05.zillinks.domain.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GoogleAccountRepository extends JpaRepository<GoogleAccount, Long> {

    @Query("select g from GoogleAccount g where g.oAuthId = :oAuthId")
    Optional<GoogleAccount> findByOAuthId(@Param("oAuthId") String oAuthId);
}
