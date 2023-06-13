package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class VisitorLogQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    private final int SIZE = 8;

    public VisitorLogQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

}
