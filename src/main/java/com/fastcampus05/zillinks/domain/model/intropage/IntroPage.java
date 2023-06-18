package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.dashboard.Dashboard;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fastcampus05.zillinks.domain.model.widget.Widget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intro_page_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Slf4j
public class IntroPage extends TimeBaseEntity {

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intro_page_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private IntroPageStatus introPageStatus; // [PUBLIC, PRIVATE] 숨김/공개여부

    @Embedded
    private Theme theme;

    @OneToOne(mappedBy = "introPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompanyInfo companyInfo;

    @OneToOne(mappedBy = "introPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private SiteInfo siteInfo;

    @OneToOne(mappedBy = "introPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private HeaderAndFooter headerAndFooter;

    @OneToMany(mappedBy = "introPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Widget> widgets = new ArrayList<>();

    @OneToMany(mappedBy = "introPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Dashboard> dashboards = new ArrayList<>();

    // == 연관관계 메서드 == //
    public void setUser(User user) {
        this.user = user;
        user.setIntroPage(this);
    }

    public void setSiteInfo(SiteInfo siteInfo) {
        this.siteInfo = siteInfo;
        siteInfo.setIntroPage(this);
    }

    public void addWidgets(Widget widget) {
        this.widgets.add(widget);
        widget.setWidgetStatus(false);
        widget.setIntroPage(this);
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
        companyInfo.setIntroPage(this);
    }

    public void setHeaderAndFooter(HeaderAndFooter headerAndFooter) {
        this.headerAndFooter = headerAndFooter;
        headerAndFooter.setIntroPage(this);
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }

    public static IntroPage saveIntroPage(User user) {
        return IntroPage.builder()
                .user(user)
                .introPageStatus(IntroPageStatus.PRIVATE)
                .theme(new Theme("ThemeA", "#000000"))
                .widgets(new ArrayList<>())
                .build();
    }

    // == 비즈니스 로직 == //
    public void updateMainPage(Boolean status, List<Boolean> widgetStatusList) {
        IntroPageStatus introPageStatus = status.equals(true) ? IntroPageStatus.PUBLIC : IntroPageStatus.PRIVATE;
        this.introPageStatus = introPageStatus;
        for (int i = 0; i < this.widgets.size(); i++) {
            Widget widget = this.widgets.get(i);
            widget.setWidgetStatus(widgetStatusList.get(i));
        }
    }

    public void updateSaveStatus(IntroPageStatus introPageStatus) {
        this.introPageStatus = introPageStatus;
    }
}
