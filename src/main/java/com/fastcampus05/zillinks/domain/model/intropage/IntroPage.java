package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    @Column(name = "intod_page_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String color;

    @Embedded
    private WebPageInfo webPageInfo;

    @Embedded
    private ZillinksData zillinksData;

    @NotEmpty // default image
    private String logo; // S3 저장소의 경로, 코드 구현시에는 본인의 저장소를 적는다 ex) /upload/file

    private String introFile;

    private String mediaKitFile;

    @Enumerated(EnumType.STRING)
    private SaveStatus saveStatus; // [UPDATING, SAVED]

//    @OneToMany(mappedBy = "widget")
//    private List<Widget> widgets;

    public static IntroPage saveIntroPage(User user) {
        return IntroPage.builder()
                .user(user)
                .color("#ffffff")
                .webPageInfo(new WebPageInfo(DEFAULT_IMAGE, null, null, null, null))
                .zillinksData(Common.zillinksApi(user.getBizNum()).toZillinksData())
                .logo(DEFAULT_IMAGE)
                .introFile(null)
                .mediaKitFile(null)
                .saveStatus(SaveStatus.UPDATING)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
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
