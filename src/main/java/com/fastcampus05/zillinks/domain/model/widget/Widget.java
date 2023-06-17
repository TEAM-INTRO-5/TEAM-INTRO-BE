package com.fastcampus05.zillinks.domain.model.widget;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "widget_tb")
@SuperBuilder
public abstract class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "widget_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_page_id")
    @NotNull
    private IntroPage introPage;

    @NotNull
    @Column(name = "widget_status")
    private Boolean widgetStatus;

    @CreatedDate
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void setIntroPage(IntroPage introPage) {
        this.introPage = introPage;
    }

    public void setWidgetStatus(Boolean widgetStatus) {
        this.widgetStatus = widgetStatus;
    }
}
