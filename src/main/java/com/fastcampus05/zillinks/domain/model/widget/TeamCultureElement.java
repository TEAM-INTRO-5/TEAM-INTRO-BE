package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "team_culture_element_tb")
public class TeamCultureElement extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_culture_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_culture_id")
    private TeamCulture teamCulture;

    @Column(name = "orders")
    private Long order;

    private String image;
    private String culture;
    private String desciption;

    public void setOrder(Long order) {
        this.order = order;
    }
}
