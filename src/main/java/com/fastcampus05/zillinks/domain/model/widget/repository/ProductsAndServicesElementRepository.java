package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.ProductsAndServicesElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsAndServicesElementRepository extends JpaRepository<ProductsAndServicesElement, Long> {
    void deleteAllByIdIn(List<Long> id);
}
