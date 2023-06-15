package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("products_and_services")
@Table(name = "products_and_services_tb")
public class ProductsAndServices extends Widget {

    @OneToMany(mappedBy = "productsAndServices", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductsAndServicesElement> productsAndServicesElements;

    @Column(name = "call_to_action_status")
    private Boolean callToActionStatus;
    @Embedded
    private CallToAction callToAction;
}
