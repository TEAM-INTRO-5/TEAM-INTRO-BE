package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.fastcampus05.zillinks.domain.model.dashboard.ContactUsLog;
import com.fastcampus05.zillinks.domain.model.dashboard.ContactUsStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DownloadLogQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    private final int SIZE = 8;

    public DownloadLogQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<ContactUsLog> findAllByStatus(ContactUsStatus status, Long intro_page_id) {
        return null;
    }
}
