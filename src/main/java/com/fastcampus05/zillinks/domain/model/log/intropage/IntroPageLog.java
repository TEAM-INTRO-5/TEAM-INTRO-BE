package com.fastcampus05.zillinks.domain.model.log.intropage;

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
public abstract class IntroPageLog extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Intro_page_log_id")
    private Long id;

    @NotNull
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_page_id")
    private IntroPage introPage;

    public IntroPageLog(IntroPage introPage, String email) {
        this.introPage = introPage;
        this.email = email;
    }
}
