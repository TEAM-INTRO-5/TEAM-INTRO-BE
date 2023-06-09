package com.fastcampus05.zillinks.domain.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select u from User u where u.loginId = :loginId")
    Optional<User> findByLoginId(@Param("loginId") String loginId);

    @Query("select u from User u where u.bizNum = :bizNum")
    Optional<User> findByBizNum(@Param("bizNum") String bizNum);

    @Query("select u from User u where u.loginId = :loginId and u.email = :email")
    Optional<User> findByLoginIdAndEmail(@Param("loginId") String loginId, @Param("email") String email);
}