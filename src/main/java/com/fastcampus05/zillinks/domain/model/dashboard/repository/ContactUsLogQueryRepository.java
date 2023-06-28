package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.fastcampus05.zillinks.domain.model.dashboard.ContactUsLog;
import com.fastcampus05.zillinks.domain.model.dashboard.ContactUsStatus;
import com.fastcampus05.zillinks.domain.model.dashboard.DownloadLog;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.dashboard.QContactUsLog.contactUsLog;
import static com.fastcampus05.zillinks.domain.model.dashboard.QDownloadLog.downloadLog;
import static com.fastcampus05.zillinks.domain.model.widget.QHistoryElement.historyElement;


@Repository
public class ContactUsLogQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;


    public ContactUsLogQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<ContactUsLog> findPGAllByStatus(ContactUsStatus contactUsStatus, Long introPageId, Integer page) {
        int size = 10;
        int startPosition = page * size;

        List<ContactUsLog> contactUsLogListPS = query
                .selectFrom(contactUsLog)
                .where(eqIntroPageId(introPageId), eqStatus(contactUsStatus))
                .orderBy(contactUsLog.createdAt.desc())
                .offset(startPosition)
                .limit(size)
                .fetch();

        Long totalCount = query
                .selectFrom(contactUsLog)
                .where(eqIntroPageId(introPageId), eqStatus(contactUsStatus))
                .stream().count();
        return new PageImpl<>(contactUsLogListPS, PageRequest.of(page, size), totalCount);
    }

    public List<ContactUsLog> findAllByStatus(ContactUsStatus contactUsStatus, Long introPageId) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        return query
                .selectFrom(contactUsLog)
                .where(eqIntroPageId(introPageId), eqStatus(contactUsStatus), goeOneMonthAgo(oneMonthAgo))
                .orderBy(contactUsLog.createdAt.desc())
                .fetch();
    }

    public List<ContactUsLog> findByContactUsList(List<Long> contactUsIdList) {
        if (contactUsIdList == null || contactUsIdList.isEmpty()) {
            return new ArrayList<>();
        }

        return query
                .selectFrom(contactUsLog)
                .where(inContactUsList(contactUsIdList))
                .fetch();
    }

    public List<ContactUsLog> findAllInWeek(Long introPageId) {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

        return query
                .selectFrom(contactUsLog)
                .where(eqIntroPageId(introPageId), goeOneMonthAgo(oneWeekAgo))
                .fetch();
    }

    private static BooleanExpression eqIntroPageId(Long introPageId) {
        if (introPageId != null) {
            return contactUsLog.introPage.id.eq(introPageId);
        }
        return null;
    }

    private static BooleanExpression eqStatus(ContactUsStatus contactUsStatus) {
        if (contactUsStatus != null) {
            return contactUsLog.contactUsStatus.eq(contactUsStatus);
        }
        return null;
    }

    private static BooleanExpression goeOneMonthAgo(LocalDate oneMonthAgo) {
        if (oneMonthAgo != null) {
            return contactUsLog.createdAt.goe(oneMonthAgo.atStartOfDay());
        }
        return null;
    }

    private static BooleanExpression inContactUsList(List<Long> contactUsIdList) {
        if (contactUsIdList != null) {
            return contactUsLog.id.in(contactUsIdList);
        }
        return null;
    }
}
