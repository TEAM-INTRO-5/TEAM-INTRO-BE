package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SharingLogQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public SharingLogQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

}
