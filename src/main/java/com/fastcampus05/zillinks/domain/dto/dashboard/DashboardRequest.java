package com.fastcampus05.zillinks.domain.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DashboardRequest {

    @Getter
    public static class ContactUsInDTO {
        @Schema(description = "요청할 회사의 ID", example = "1")
        @JsonProperty("intro_page_id")
        private Long introPageId;

        @Schema(description = "문의 내용", example = "IR 자료 요청")
        @Pattern(regexp = "IR 자료 요청|상품 문의|채용 문의")
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
    public static class DownloadInDTO {
        @Schema(description = "요청할 회사의 ID", example = "1")
        @JsonProperty("intro_page_id")
        private Long introPageId;

        @Schema(description = "다운 받을 자료 종류", example = "intro_file")
        @Pattern(regexp = "INTROFILE|MEDIAKIT")
        private String type;

        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @Schema(description = "이메일", example = "taeheoki@naver.com")
        private String email;
    }

    @Getter
    public static class UpdateContactUsDetailInDTO {
        @Schema(description = "연락관리내역 요소 ID", example = "1")
        @JsonProperty("contact_us_id")
        private Long contactUsId;

        @Schema(description = "변경 상태 종류", example = "CONFIRM")
        @Pattern(regexp = "CONFIRM|CANCEL")
        private String status;
    }

    @Getter
    public static class ExcelContactUsInDTO {
        @Schema(description = "확인 필요/완료", example = "UNCONFIRMED")
        @Pattern(regexp = "UNCONFIRMED|CONFIRM")
        private String status;
    }

    @Getter
    public static class ExcelDownloadInDTO {
        @Schema(description = "다운로드 내역", example = "UNCONFIRMED")
        @Pattern(regexp = "ALL|INTROFILE|MEDIAKIT")
        private String type;
    }

    @Getter
    public static class ExcelVisitorInDTO {
        @Schema(description = "방문자 기록", example = "VIEW")
        @Pattern(regexp = "VIEW|SHARING")
        private String type;
    }
}
