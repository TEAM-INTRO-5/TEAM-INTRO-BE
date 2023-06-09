package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class IntroPageResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveIntroPageOutDTO {
        private Long introPageId;
        private String color;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class IntroPageOutDTO {
        private Long introPageId;
        private String color;

//         check-point, widget 관련 정리 후 추가
//         private List<Widget> widgets;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateIntroPageOutDTO {
        private Long introPageId;
        private String color;

//         check-point, widget 관련 정리 후 추가
//         private List<Widget> widgets;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class InfoOutDTO {
        private Long introPageId;
        private WebPageInfoOutDTO webPageInfoOutDTO;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class WebPageInfoOutDTO {
            private String pavicon; // 경로

            private String webPageName;

            private String subDomain;

            private String title;

            private String description;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateInfoOutDTO {
        private Long introPageId;
        private WebPageInfoOutDTO webPageInfoOutDTO;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class WebPageInfoOutDTO {
            private String pavicon; // 경로

            private String webPageName;

            private String subDomain;

            private String title;

            private String description;
        }
    }
}
