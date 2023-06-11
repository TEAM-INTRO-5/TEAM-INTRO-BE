package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "company_info_tb")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CompanyInfo extends TimeBaseEntity {

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";

    @Id
    @GeneratedValue
    @Column(name = "company_info_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_page_id")
    private IntroPage introPage;

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String bizNum;

    @NotEmpty
    private String contactEmail;

    @Lob
    private String tagline;

    @Column(unique = true)
    private String logo; // S3 저장소의 경로, 코드 구현시에는 본인의 저장소를 적는다 ex) /upload/file

    @Column(unique = true)
    private String introFile;

    @Column(unique = true)
    private String mediaKitFile;

    // == 연관관계 메서드 == //
    public void setIntroPage(IntroPage introPage) {
        this.introPage = introPage;
        introPage.setCompanyInfo(this);
    }

    public static CompanyInfo saveCompanyInfo(IntroPage introPage) {
        ZillinksData zillinksData = Common.zillinksApi(introPage.getUser().getBizNum()).toZillinksData();
        return CompanyInfo.builder()
                .introPage(introPage)
                .companyName(zillinksData.getName())
                .bizNum(zillinksData.getBizNum())
                .contactEmail(zillinksData.getContactEmail())
                .tagline(zillinksData.getTagline())
                .logo(null)
                .introFile(null)
                .mediaKitFile(null)
                .build();
    }

    public void changeCompanyInfo(String logo, String introFile, String mediaKitFile) {
        this.logo = logo;
        this.introFile = introFile;
        this.mediaKitFile = mediaKitFile;
    }
}
