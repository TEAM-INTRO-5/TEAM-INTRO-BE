package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.HistoryElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryElementRepository extends JpaRepository<HistoryElement, Long> {
    void deleteAllByIdIn(List<Long> id);
}
