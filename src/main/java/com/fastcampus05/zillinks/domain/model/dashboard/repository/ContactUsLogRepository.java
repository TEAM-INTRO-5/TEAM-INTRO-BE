package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.fastcampus05.zillinks.domain.model.dashboard.ContactUsLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactUsLogRepository extends JpaRepository<ContactUsLog, Long> {
}
