package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "intro_page_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
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
    private IntroPageStatus introPageStatus; // [HIDDEN, OPEN] 숨김/공개여부

    @Embedded
    private Theme theme;

    @OneToOne(mappedBy = "introPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompanyInfo companyInfo;

    @Embedded
    private SiteInfo siteInfo;

//    @OneToMany(mappedBy = "widget", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Widget> widgets;

    // == 연관관계 메서드 == //
    public void setUser(User user) {
        this.user = user;
        user.setIntroPage(this);
    }

    public static IntroPage saveIntroPage(User user) {

        return IntroPage.builder()
                .user(user)
                .introPageStatus(IntroPageStatus.PRIVATE)
                .theme(new Theme("ThemeA", "#ffffff"))
                .siteInfo(SiteInfo.builder()
                        .pavicon(null)
                        .title("")
                        .description("")
                        .build())
                // 이후 widget도 시작하자 마자 다 생성하는 방향으로 진행
                .build();
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    // == 비즈니스 로직 == //
    public void updateMainPage(Boolean status, List<Boolean> widgetStatusList) {
        IntroPageStatus introPageStatus = status.equals(true) ? IntroPageStatus.PUBLIC : IntroPageStatus.PRIVATE;
        this.introPageStatus = introPageStatus;
        // check-point
        // widgetStatusList 추가
    }

    public void updateSaveStatus(IntroPageStatus introPageStatus) {
        this.introPageStatus = introPageStatus;
    }
}
