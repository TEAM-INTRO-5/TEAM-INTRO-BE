package com.fastcampus05.zillinks.domain.model.intropage;

import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "company_info_tb")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CompanyInfo extends TimeBaseEntity {

    public static final String DEFAULT_IMAGE = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_info_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_page_id", unique = true)
    private IntroPage introPage;

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String startDate;

    @NotEmpty
    private String representative;

    @Column(unique = true)
    private String logo; // S3 저장소의 경로, 코드 구현시에는 본인의 저장소를 적는다 ex) /upload/file

    @NotEmpty
    private String contactEmail;

    @NotEmpty
    private String bizNum;

    private String phoneNumber;

    private String faxNumber;

    public void setIntroPage(IntroPage introPage) {
        this.introPage = introPage;
    }

    public static CompanyInfo saveCompanyInfo(IntroPage introPage) {
        ZillinksData zillinksData = Common.zillinksApi(introPage.getUser().getBizNum()).toZillinksData();
        return CompanyInfo.builder()
                .companyName(zillinksData.getName())
                .startDate(zillinksData.getStartDate())
                .representative(zillinksData.getRepresentative())
                .contactEmail(zillinksData.getContactEmail())
                .bizNum(zillinksData.getBizNum())
                .build();
    }


    public void updateCompanyInfo(String representative, String logo, String contactEmail, String phoneNumber, String faxNumber) {
        this.representative = representative;
        this.logo = logo;
        this.contactEmail = contactEmail;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
    }
}
