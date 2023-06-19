package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.fastcampus05.zillinks.domain.model.widget.ProductsAndServicesElement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QProductsAndServicesElement.productsAndServicesElement;
import static com.fastcampus05.zillinks.domain.model.widget.QTeamMemberElement.teamMemberElement;

@Repository
public class ProductsAndServicesElementQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ProductsAndServicesElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<ProductsAndServicesElement> findAllByDeleteList(List<Long> deleteList) {
        return query.selectFrom(productsAndServicesElement)
                .where(inDeleteList(deleteList))
                .fetch();
    }

    public void deleteByDeleteList(List<Long> deleteList) {
        query.delete(productsAndServicesElement)
                .where(inDeleteList(deleteList))
                .execute();
    }

    private BooleanExpression inDeleteList(List<Long> deleteList) {
        if (deleteList != null)
            return productsAndServicesElement.id.in(deleteList);
        return null;
    }
}
