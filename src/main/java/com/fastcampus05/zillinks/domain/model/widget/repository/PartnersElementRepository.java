package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.PartnersElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnersElementRepository extends JpaRepository<PartnersElement, Long> {
    void deleteAllByIdIn(List<Long> id);
}
