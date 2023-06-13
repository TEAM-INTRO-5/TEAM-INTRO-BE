package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.fastcampus05.zillinks.domain.model.dashboard.ContactUsLog;
import com.fastcampus05.zillinks.domain.model.dashboard.ContactUsStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.dashboard.QContactUsLog.contactUsLog;


@Repository
public class ContactUsLogQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    private final int SIZE = 8;

    public ContactUsLogQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<ContactUsLog> findAllByStatus(ContactUsStatus status, Long introPageId, Integer page) {
        int startPosition = page * SIZE;

        List<ContactUsLog> contactUsLogListPS = query
                .selectFrom(contactUsLog)
                .where(getBooleanExpression(status, introPageId))
                .orderBy(contactUsLog.createdAt.desc())
                .offset(startPosition)
                .limit(SIZE)
                .fetch();

        Long totalCount = query
                .selectFrom(contactUsLog)
                .where(getBooleanExpression(status, introPageId))
                .stream().count();
        return new PageImpl<>(contactUsLogListPS, PageRequest.of(page, SIZE), totalCount);
    }

    private static BooleanExpression getBooleanExpression(ContactUsStatus status, Long intro_page_id) {
        return contactUsLog.introPage.id.eq(intro_page_id).and(contactUsLog.contactUsStatus.eq(status));
    }
}
