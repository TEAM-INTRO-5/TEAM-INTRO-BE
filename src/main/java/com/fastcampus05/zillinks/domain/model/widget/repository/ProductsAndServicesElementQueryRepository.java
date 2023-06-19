package com.fastcampus05.zillinks.domain.model.widget.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fastcampus05.zillinks.domain.model.widget.QProductsAndServicesElement.productsAndServicesElement;

@Repository
public class ProductsAndServicesElementQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    private final int SIZE = 8;

    public ProductsAndServicesElementQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
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
