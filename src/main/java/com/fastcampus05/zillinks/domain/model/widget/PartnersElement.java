package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "partners_element_tb")
public class PartnersElement extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partners_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partners_id")
    private Partners partners;

    @Column(name = "orders")
    private Long order;

    @Column(name = "partners_type")
    @Enumerated(EnumType.STRING)
    private PartnersType partnersType;

    @Column(name = "company_name")
    private String companyName;
    private String logo;

    public void setOrder(Long order) {
        this.order = order;
    }
}
