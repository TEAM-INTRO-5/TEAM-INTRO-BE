package com.fastcampus05.zillinks.domain.model.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "partners_element_tb")
public class PartnersElement {
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
    private PartnersType partnersType;

    @Column(name = "company_name")
    private String companyName;
    private String logo;
}
