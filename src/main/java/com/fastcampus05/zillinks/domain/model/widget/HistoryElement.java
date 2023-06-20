package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "history_element_tb")
public class HistoryElement extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;

    @Column(name = "orders")
    private Long order;

    private String image;
    private LocalDate date;
    private String title;
    private String description;

    public void setOrder(Long order) {
        this.order = order;
    }
}
