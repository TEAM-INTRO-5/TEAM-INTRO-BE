package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products_and_services_element_tb")
public class ProductsAndServicesElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_and_services_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_and_services_id")
    private ProductsAndServices productsAndServices;

    private String image;
    private String name;
    private String title;
    private String description;
}
