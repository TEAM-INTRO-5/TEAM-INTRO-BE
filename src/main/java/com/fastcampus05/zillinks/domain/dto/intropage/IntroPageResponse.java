package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

public class IntroPageResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveIntroPageOutDTO {
        private String color;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class IntroPageOutDTO {
        private String color;

//         check-point, widget 관련 정리 후 추가
//         private List<Widget> widgets;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateIntroPageOutDTO {
        private String color;

//         check-point, widget 관련 정리 후 추가
//         private List<Widget> widgets;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class InfoOutDTO {
        private String pavicon; // 경로
        private String webPageName;
        private String domain;
        private String title;
        private String description;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateInfoOutDTO {
        private String pavicon; // 경로
        private String webPageName;
        private String domain;
        private String title;
        private String description;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CompanyInfoOutDTO {
        private String companyName;
        private String bizNum;
        private String contactEmail;
        private String tagline;
        private String logo;
        private String introFile;
        private String mediaKitFile;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateCompanyInfoOutDTO {
        private String companyName;
        private String bizNum;
        private String contactEmail;
        private String tagline;
        private String logo;
        private String introFile;
        private String mediaKitFile;
    }
}
