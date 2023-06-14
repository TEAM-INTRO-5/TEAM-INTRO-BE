package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.SaveStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntroPageResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class IntroPageOutDTO {

        private Long introPageId;
        private SaveStatus saveStatus;
        private Theme theme;
        private CompanyInfo companyInfo;
        private SiteInfo siteInfo;
//         check-point, widget 관련 정리 후 추가
//         private List<Widget> widgets;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class Theme {
            private String type;
            private String color;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class CompanyInfo {
            private String companyName;
            private String startDate;
            private String representative;
            private String logo;
            private String contactEmail;
            private String bizNum;
            private String phoneNumber;
            private String faxNumber;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class SiteInfo {
            private String pavicon; // 경로
            private String subDomain;
            private String title;
            private String description;
        }

        public static IntroPageOutDTO toOutDTO(IntroPage introPage) {
            return IntroPageOutDTO.builder()
                    .introPageId(introPage.getId())
                    .saveStatus(introPage.getSaveStatus())
                    .theme(Theme.builder()
                            .type(introPage.getTheme().getType())
                            .color(introPage.getTheme().getColor())
                            .build())
                    .companyInfo(CompanyInfo.builder()
                            .companyName(introPage.getCompanyInfo().getCompanyName())
                            .startDate(introPage.getCompanyInfo().getStartDate())
                            .representative(introPage.getCompanyInfo().getRepresentative())
                            .logo(introPage.getCompanyInfo().getLogo())
                            .contactEmail(introPage.getCompanyInfo().getContactEmail())
                            .bizNum(introPage.getCompanyInfo().getBizNum())
                            .phoneNumber(introPage.getCompanyInfo().getPhoneNumber())
                            .faxNumber(introPage.getCompanyInfo().getFaxNumber())
                            .build())
                    .siteInfo(SiteInfo.builder()
                            .pavicon(introPage.getSiteInfo().getPavicon())
                            .subDomain(introPage.getSiteInfo().getSubDomain())
                            .title(introPage.getSiteInfo().getTitle())
                            .description(introPage.getSiteInfo().getDescription())
                            .build())
                    .build();
        }
    }
}
