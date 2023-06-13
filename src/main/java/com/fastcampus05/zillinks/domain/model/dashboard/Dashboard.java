package com.fastcampus05.zillinks.domain.model.dashboard;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dashboard_tb")
public abstract class Dashboard extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dashboard_id")
    private Long id;

    @NotNull
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_page_id")
    private IntroPage introPage;

    public Dashboard(IntroPage introPage, String email) {
        this.introPage = introPage;
        this.email = email;
    }
}
