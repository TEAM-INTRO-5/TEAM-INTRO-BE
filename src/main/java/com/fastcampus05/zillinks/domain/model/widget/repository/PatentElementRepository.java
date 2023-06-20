package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.PatentElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatentElementRepository extends JpaRepository<PatentElement, Long> {
    void deleteAllByIdIn(List<Long> id);
}
