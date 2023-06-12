package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    @Getter
    public static class ContactUsInDTO {
        @Schema(description = "요청할 회사의 ID", example = "1")
        @JsonProperty("intro_page_id")
        private Long introPageId;

        @Schema(description = "문의 내용", example = "IR 자료 요청")
        @Pattern(regexp = "IR_data_req|상품문의|채용문의")
        private String type;

        @Schema(description = "성명", example = "홍길동")
        @NotBlank
        private String name;

        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @Schema(description = "연락처", example = "taeheoki@naver.com")
        private String email;

        @Schema(description = "내용", example = "이러쿵 저러쿵 이건 내용입니다.")
        @NotNull
        private String content;
    }

    @Getter
    public static class DownloadFileInDTO {
        @Schema(description = "요청할 회사의 ID", example = "1")
        @JsonProperty("intro_page_id")
        private Long introPageId;

        @Schema(description = "다운 받을 자료 종류", example = "intro_file")
        @Pattern(regexp = "intro_file|media_kit_file")
        private String type;

        @Schema(description = "모르겠음", example = "uaua")
        private String keyword;

        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @Schema(description = "이메일", example = "taeheoki@naver.com")
        private String email;
    }
}
