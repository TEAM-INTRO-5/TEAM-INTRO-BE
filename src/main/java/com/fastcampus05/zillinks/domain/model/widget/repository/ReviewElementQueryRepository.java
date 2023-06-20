package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.ReviewElement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QReviewElement.reviewElement;

@Repository
public class ReviewElementQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public ReviewElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<ReviewElement> findAllByDeleteList(List<Long> deleteList) {
        return query.selectFrom(reviewElement)
                .where(inDeleteList(deleteList))
                .fetch();
    }

    public void deleteByDeleteList(List<Long> deleteList) {
        query.delete(reviewElement)
                .where(inDeleteList(deleteList))
                .execute();
    }

    private BooleanExpression inDeleteList(List<Long> deleteList) {
        if (deleteList != null)
            return reviewElement.id.in(deleteList);
        return null;
    }
}
