package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.fastcampus05.zillinks.domain.model.dashboard.VisitorLog;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.dashboard.QVisitorLog.visitorLog;

@Repository
public class VisitorLogQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public VisitorLogQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<VisitorLog> findAllByType(Long introPageId, Integer page) {
        int size = 8;
        int startPosition = page * size;

        List<VisitorLog> visitorLogListPS = query
                .selectFrom(visitorLog)
                .where(eqIntroPageId(introPageId))
                .orderBy(visitorLog.createdAt.desc())
                .offset(startPosition)
                .limit(size)
                .fetch();

        Long totalCount = query
                .selectFrom(visitorLog)
                .where(eqIntroPageId(introPageId))
                .stream().count();
        return new PageImpl<>(visitorLogListPS, PageRequest.of(page, size), totalCount);
    }

    private BooleanExpression eqIntroPageId(Long introPageId) {
        if (introPageId != null) {
            return visitorLog.introPage.id.eq(introPageId);
        }
        return null;
    }
}
