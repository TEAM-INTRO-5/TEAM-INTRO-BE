package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.PatentElement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QPatentElement.patentElement;

@Repository
public class PatentElementQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PatentElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<PatentElement> findAllByDeleteList(List<Long> deleteList) {
        return query.selectFrom(patentElement)
                .where(inDeleteList(deleteList))
                .fetch();
    }

    public void deleteByDeleteList(List<Long> deleteList) {
        query.delete(patentElement)
                .where(inDeleteList(deleteList))
                .execute();
    }

    private BooleanExpression inDeleteList(List<Long> deleteList) {
        if (deleteList != null)
            return patentElement.id.in(deleteList);
        return null;
    }
}
