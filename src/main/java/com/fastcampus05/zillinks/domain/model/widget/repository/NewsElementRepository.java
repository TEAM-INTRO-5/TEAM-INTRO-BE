package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.NewsElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsElementRepository extends JpaRepository<NewsElement, Long> {
    void deleteAllByIdIn(List<Long> id);
}
