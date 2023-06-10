package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @JoinColumn(name = "user_id")
    private User user;

    private String color;

//    @OneToMany(mappedBy = "widget")
//    private List<Widget> widgets;

    @Embedded
    private WebPageInfo webPageInfo;

    @Enumerated(EnumType.STRING)
    private SaveStatus saveStatus; // [UPDATING, SAVED]

    @OneToOne(mappedBy = "introPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompanyInfo companyInfo;

    // == 연관관계 메서드 == //
    public void setUser(User user) {
        this.user = user;
        user.setIntroPage(this);
    }

    public static IntroPage saveIntroPage(User user) {
        return IntroPage.builder()
                .user(user)
                .color("#ffffff")
                .webPageInfo(new WebPageInfo(DEFAULT_IMAGE, null, null, null, null))
                .saveStatus(SaveStatus.UPDATING)
                .build();
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public void changeIntroPage(String color) {

        this.color = color;
        this.saveStatus = SaveStatus.SAVED;
        // check-point
        // Widget 부분 추가
    }

    public void changeIntroPageInfo(String pavicon, String webPageName, String subDomain, String title, String description) {
        this.webPageInfo = new WebPageInfo(pavicon, webPageName, subDomain, title, description);

        // check-point
        // Widget 부분 추가
    }

}
