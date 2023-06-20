package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.HistoryElement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QHistoryElement.historyElement;

@Repository
public class HistoryElementQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public HistoryElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<HistoryElement> findAllByDeleteList(List<Long> deleteList) {
        return query.selectFrom(historyElement)
                .where(inDeleteList(deleteList))
                .fetch();
    }

    public void deleteByDeleteList(List<Long> deleteList) {
        query.delete(historyElement)
                .where(inDeleteList(deleteList))
                .execute();
    }

    private BooleanExpression inDeleteList(List<Long> deleteList) {
        if (deleteList != null)
            return historyElement.id.in(deleteList);
        return null;
    }
}
