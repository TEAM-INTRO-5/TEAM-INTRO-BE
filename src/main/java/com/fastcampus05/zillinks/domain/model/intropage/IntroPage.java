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

    private String pavicon; // 경로

    @NotEmpty
    private String webPageName;

    @NotEmpty
    private String subDomain;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @Embedded
    private ZillinksData zillinksData;

    @NotEmpty // default image
    private String logo; // S3 저장소의 경로, 코드 구현시에는 본인의 저장소를 적는다 ex) /upload/file

    private String introFile;

    private String mediaKitFile;

    private String trackingCode;

    @Enumerated(EnumType.STRING)
    private SaveStatus saveStatus; // [TEMP_SAVED, SAVED]

    public void setUser(User user) {
        this.user = user;
    }

    public void changeIntroPageInfo(IntroPageRequest.UpdateInDTO updateInDTO) {
        this.zillinksData = new ZillinksData(updateInDTO.getName(), updateInDTO.getBizNum(), updateInDTO.getContactEmail(), updateInDTO.getTagline());
        this.logo = updateInDTO.getLogo();
        this.introFile = updateInDTO.getIntroFile();
        this.mediaKitFile = updateInDTO.getMediaKitFile();
    }

//    @OneToMany(mappedBy = "widget")
//    private List<Widget> widgets;
}
