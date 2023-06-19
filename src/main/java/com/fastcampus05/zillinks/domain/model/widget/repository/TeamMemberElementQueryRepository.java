package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QTeamMemberElement.teamMemberElement;

@Repository
public class TeamMemberElementQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public TeamMemberElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public void deleteByDeleteList(List<Long> deleteList) {
        query.delete(teamMemberElement)
                .where(inDeleteList(deleteList))
                .execute();
    }

    private BooleanExpression inDeleteList(List<Long> deleteList) {
        if (deleteList != null)
            return teamMemberElement.id.in(deleteList);
        return null;
    }
}
