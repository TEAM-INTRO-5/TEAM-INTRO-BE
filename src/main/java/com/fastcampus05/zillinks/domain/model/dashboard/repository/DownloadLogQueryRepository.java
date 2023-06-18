package com.fastcampus05.zillinks.domain.model.dashboard.repository;

import com.fastcampus05.zillinks.domain.model.dashboard.DownloadLog;
import com.fastcampus05.zillinks.domain.model.dashboard.DownloadType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.dashboard.QContactUsLog.contactUsLog;
import static com.fastcampus05.zillinks.domain.model.dashboard.QDownloadLog.downloadLog;

@Repository
public class DownloadLogQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    private final int SIZE = 8;

    public DownloadLogQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<DownloadLog> findPGAllByType(DownloadType downloadType, Long introPageId, Integer page) {
        int size = 8;
        int startPosition = page * size;

        List<DownloadLog> downloadLogListPS = query
                .selectFrom(downloadLog)
                .where(eqIntroPageId(introPageId), eqDownloadType(downloadType))
                .orderBy(downloadLog.createdAt.desc())
                .offset(startPosition)
                .limit(size)
                .fetch();

        Long totalCount = query
                .selectFrom(downloadLog)
                .where(eqIntroPageId(introPageId), eqDownloadType(downloadType))
                .stream().count();
        return new PageImpl<>(downloadLogListPS, PageRequest.of(page, size), totalCount);
    }

    public List<DownloadLog> findAllByType(DownloadType downloadType, Long introPageId) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        return query
                .selectFrom(downloadLog)
                .where(eqIntroPageId(introPageId), eqDownloadType(downloadType), goeOneMonthAgo(oneMonthAgo))
                .orderBy(downloadLog.createdAt.desc())
                .fetch();
    }

    public List<DownloadLog> findAllInWeek(Long introPageId) {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

        return query
                .selectFrom(downloadLog)
                .where(eqIntroPageId(introPageId), goeOneMonthAgo(oneWeekAgo))
                .orderBy(downloadLog.createdAt.desc())
                .fetch();
    }

    private BooleanExpression eqIntroPageId(Long introPageId) {
        if (introPageId != null)
            return downloadLog.introPage.id.eq(introPageId);
        return null;
    }

    private BooleanExpression eqDownloadType(DownloadType downloadType) {
        if (downloadType != null)
            return downloadLog.downloadType.eq(downloadType);
        return null;
    }

    private static BooleanExpression goeOneMonthAgo(LocalDate oneMonthAgo) {
        if (oneMonthAgo != null) {
            return downloadLog.createdAt.goe(oneMonthAgo.atStartOfDay());
        }
        return null;
    }

    private static BooleanExpression goeOneWeekAgo(LocalDate oneWeekAgo) {
        if (oneWeekAgo != null) {
            return downloadLog.createdAt.goe(oneWeekAgo.atStartOfDay());
        }
        return null;
    }
}
