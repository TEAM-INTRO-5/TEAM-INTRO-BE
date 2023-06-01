package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.model.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "intro_page_tb")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class IntroPage extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intod_page_id")
    private Long id;

    @Embedded
    private ZillinkData zillinkData;

    @NotEmpty // default image
    private String logo; // S3 저장소의 경로, 코드 구현시에는 본인의 저장소를 적는다 ex) /upload/file

    private String introFile;

    private String mediaKitFile;

    private String trackingCode;

    @Enumerated(EnumType.STRING)
    private SaveStatus status; // [TEMP_SAVED, SAVED, UPDATING]

    @OneToOne(mappedBy = "introPage")
    private User user;

    public void mapTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public void changeIntroPageInfo(IntroPageRequest.UpdateInDTO updateInDTO) {
        this.zillinkData = new ZillinkData(updateInDTO.getName(), updateInDTO.getBizNum(), updateInDTO.getContactEmail(), updateInDTO.getTagline());
        this.logo = updateInDTO.getLogo();
        this.introFile = updateInDTO.getIntroFile();
        this.mediaKitFile = updateInDTO.getMediaKitFile();
    }

//    @OneToMany(mappedBy = "widget")
//    private List<Widget> widgets;
}
