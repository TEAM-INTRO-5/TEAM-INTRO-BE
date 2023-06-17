package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("products_and_services")
@Table(name = "products_and_services_tb")
@SuperBuilder
public class ProductsAndServices extends Widget {

    @OneToMany(mappedBy = "productsAndServices", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductsAndServicesElement> productsAndServicesElements = new ArrayList<>();

    @Column(name = "call_to_action_status")
    private Boolean callToActionStatus;
    @Embedded
    private CallToAction callToAction;
}
