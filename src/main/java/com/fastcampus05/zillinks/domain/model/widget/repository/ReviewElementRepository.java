package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.ReviewElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewElementRepository extends JpaRepository<ReviewElement, Long> {
    void deleteAllByIdIn(List<Long> id);
}
