package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products_and_services_element_tb")
public class ProductsAndServicesElement extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_and_services_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_and_services_id")
    private ProductsAndServices productsAndServices;

    @Column(name = "orders")
    private Long order;
    private String image;
    private String name;
    private String title;
    private String description;

    public ProductsAndServicesElement(ProductsAndServices productsAndServices, Long order, String image, String name, String title, String description) {
        this.productsAndServices = productsAndServices;
        this.order = order;
        this.image = image;
        this.name = name;
        this.title = title;
        this.description = description;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}
