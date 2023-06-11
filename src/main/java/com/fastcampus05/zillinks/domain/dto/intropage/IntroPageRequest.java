package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class IntroPageRequest {

    @Getter
    public static class UpdateInDTO {
        // check-point color 패턴 추가
        @Schema(description = "색상", example = "#FF5733")
        private String color;

//         check-point, widget 관련 정리 후 추가
//         private List<Widget> widgets;
    }

    @Getter
    public static class UpdateInfoInDTO {

        @Schema(description = "pavicon 저장 경로", example = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg")
        private String pavicon; // 경로

        @Schema(description = "웹페이지 이름 설정", example = "질링스")
        @JsonProperty("web_page_name")
        private String webPageName;

        @Schema(description = "도메인 설정", example = "zillinks")
        private String domain;

        @Schema(description = "사이트 이름 ", example = "질링스")
        private String title;

        @Schema(description = "사이트 설명", example = "질링스 짱짱맨")
        private String description;
    }

    @Getter
    public static class UpdateCompanyInfoInDTO {
        @Schema(description = "회사 로고", example = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg")
        private String logo;

        @Schema(description = "회사 소개서")
        @JsonProperty("intro_file")
        private String introFile;

        @Schema(description = "미디어 킷")
        @JsonProperty("media_kit_file")
        private String mediaKitFile;
    }
}
