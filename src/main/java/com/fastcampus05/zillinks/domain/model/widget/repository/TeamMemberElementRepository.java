package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.ProductsAndServicesElement;
import com.fastcampus05.zillinks.domain.model.widget.TeamMemberElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberElementRepository extends JpaRepository<TeamMemberElement, Long> {
    void deleteAllByIdIn(List<Long> id);
}
