package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "intro_page_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class IntroPage extends TimeBaseEntity {

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

    public void setUser(User user) {
        this.user = user;
    }

    public void changeIntroPage(IntroPageRequest.UpdateInDTO updateInDTO) {

        this.color = updateInDTO.getColor();

        // check-point
        // Widget 부분 추가
    }

    public void changeIntroPageInfo(IntroPageRequest.UpdateInfoInDTO updateInfoInDTO) {
        this.webPageInfo = new WebPageInfo(updateInfoInDTO.getWebPageInfo());

        // check-point
        // Widget 부분 추가
    }

}
