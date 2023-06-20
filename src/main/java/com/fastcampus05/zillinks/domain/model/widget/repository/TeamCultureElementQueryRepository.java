package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.QTeamCultureElement;
import com.fastcampus05.zillinks.domain.model.widget.TeamCultureElement;
import com.fastcampus05.zillinks.domain.model.widget.TeamMemberElement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QTeamCultureElement.*;
import static com.fastcampus05.zillinks.domain.model.widget.QTeamMemberElement.teamMemberElement;

@Repository
public class TeamCultureElementQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public TeamCultureElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<TeamCultureElement> findAllByDeleteList(List<Long> deleteList) {
        return query.selectFrom(teamCultureElement)
                .where(inDeleteList(deleteList))
                .fetch();
    }

    public void deleteByDeleteList(List<Long> deleteList) {
        query.delete(teamCultureElement)
                .where(inDeleteList(deleteList))
                .execute();
    }

    private BooleanExpression inDeleteList(List<Long> deleteList) {
        if (deleteList != null)
            return teamCultureElement.id.in(deleteList);
        return null;
    }
}
