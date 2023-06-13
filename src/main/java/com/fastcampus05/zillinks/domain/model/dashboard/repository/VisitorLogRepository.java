package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.fastcampus05.zillinks.domain.model.dashboard.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long> {
}
