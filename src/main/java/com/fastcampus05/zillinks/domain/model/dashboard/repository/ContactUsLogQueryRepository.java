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


    public ContactUsLogQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<ContactUsLog> findAllByStatus(ContactUsStatus status, Long introPageId, Integer page) {
        int size = 10;
        int startPosition = page * size;

        List<ContactUsLog> contactUsLogListPS = query
                .selectFrom(contactUsLog)
                .where(eqIntroPageId(introPageId), eqStatus(status))
                .orderBy(contactUsLog.createdAt.desc())
                .offset(startPosition)
                .limit(size)
                .fetch();

        Long totalCount = query
                .selectFrom(contactUsLog)
                .where(eqIntroPageId(introPageId), eqStatus(status))
                .stream().count();
        return new PageImpl<>(contactUsLogListPS, PageRequest.of(page, size), totalCount);
    }

    private static BooleanExpression eqIntroPageId(Long introPageId) {
        if (introPageId != null) {
            return contactUsLog.introPage.id.eq(introPageId);
        }
        return null;
    }

    private static BooleanExpression eqStatus(ContactUsStatus status) {
        if (status != null) {
            return contactUsLog.contactUsStatus.eq(status);
        }
        return null;
    }
}
