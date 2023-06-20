package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Comparator;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "history_element_tb")
public class HistoryElement extends TimeBaseEntity implements Comparable<HistoryElement> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;

    private String image;
    private LocalDate date;
    private String title;
    private String description;

    @Override
    public int compareTo(HistoryElement o) {
        return this.date.compareTo(o.getDate());
    }
}
