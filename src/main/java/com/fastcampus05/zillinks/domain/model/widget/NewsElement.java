package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "news_element_tb")
public class NewsElement extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    @Column(name = "orders")
    private Long order;

    private String image;
    private LocalDate date;
    private String press;
    private String title;
    private String description;

    public void setOrder(Long order) {
        this.order = order;
    }
}
