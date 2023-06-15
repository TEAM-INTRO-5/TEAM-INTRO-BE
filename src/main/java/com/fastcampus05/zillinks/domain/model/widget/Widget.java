package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "widget_tb")
public abstract class Widget extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "widget_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_page_id")
    private IntroPage introPage;

    @Column(name = "widget_status")
    private Boolean widgetStatus;
}
