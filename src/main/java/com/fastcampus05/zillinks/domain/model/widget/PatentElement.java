package com.fastcampus05.zillinks.domain.model.widget;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "patent_element_tb")
public class PatentElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patent_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patent_id")
    private Patent patent;

    @Column(name = "orders")
    private Long order;

    @Column(name = "patent_type")
    @Enumerated(EnumType.STRING)
    private PatentType patentType;

    private String title;
    private String image;
}
