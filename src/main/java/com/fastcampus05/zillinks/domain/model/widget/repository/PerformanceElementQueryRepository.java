package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.PerformanceElement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QPerformanceElement.performanceElement;

@Repository
public class PerformanceElementQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PerformanceElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<PerformanceElement> findAllByDeleteList(List<Long> deleteList) {
        return query.selectFrom(performanceElement)
                .where(inDeleteList(deleteList))
                .fetch();
    }

    public void deleteByDeleteList(List<Long> deleteList) {
        query.delete(performanceElement)
                .where(inDeleteList(deleteList))
                .execute();
    }

    private BooleanExpression inDeleteList(List<Long> deleteList) {
        if (deleteList != null)
            return performanceElement.id.in(deleteList);
        return null;
    }
}
