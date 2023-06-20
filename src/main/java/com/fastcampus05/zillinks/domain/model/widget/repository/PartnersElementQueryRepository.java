package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.PartnersElement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QPartnersElement.partnersElement;

@Repository
public class PartnersElementQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PartnersElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<PartnersElement> findAllByDeleteList(List<Long> deleteList) {
        return query.selectFrom(partnersElement)
                .where(inDeleteList(deleteList))
                .fetch();
    }

    public void deleteByDeleteList(List<Long> deleteList) {
        query.delete(partnersElement)
                .where(inDeleteList(deleteList))
                .execute();
    }

    private BooleanExpression inDeleteList(List<Long> deleteList) {
        if (deleteList != null)
            return partnersElement.id.in(deleteList);
        return null;
    }
}
