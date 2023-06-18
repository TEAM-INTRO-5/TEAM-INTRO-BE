package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Size;
import java.util.List;

public class IntroPageRequest {

    @Getter
    public static class UpdateInDTO {
        @Schema(description = "공개 여부", example = "false")
        private Boolean status;

        @Schema(description = "위젯들의 공개 여부", example = "[true, false, true, false, true, false, true, false, true, false, true, false, true, false]")
        @Size(min = 14, max = 14, message = "위젯의 개수 14개")
        @JsonProperty("widget_status_list")
        private List<Boolean> widgetStatusList;

        @Schema(description = "위젯들의 순서 리스트", example = "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]")
        @Size(min = 14, max = 14, message = "위젯의 개수 14개")
        @JsonProperty("order_list")
        private List<Boolean> orderList;

    }

    @Getter
    public static class UpdateSiteInfoInDTO {

        @Schema(description = "pavicon 저장 경로", example = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg")
        private String pavicon; // 경로

        @Schema(description = "서브도메인 설정", example = "zillinks")
        @JsonProperty("sub_domain")
        private String subDomain;

        @Schema(description = "사이트 이름 ", example = "질링스")
        private String title;

        @Schema(description = "사이트 설명", example = "질링스 짱짱맨")
        private String description;
    }

    @Getter
    public static class UpdateCompanyInfoInDTO {
        @Schema(description = "대표 성명", example = "홍길동")
        private String representative;
        @Schema(description = "회사 로고", example = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg")
        private String logo;
        @Schema(description = "연락용 이메일", example = "taeheoki@naver.com")
        @JsonProperty("contact_email")
        private String contactEmail;
        @Schema(description = "전화 번호", example = "000-0000-0000")
        @JsonProperty("phone_number")
        private String phoneNumber;
        @Schema(description = "팩스 번호", example = "000-0000-0000")
        @JsonProperty("fax_number")
        private String faxNumber;
    }

    @Getter
    public static class UpdateHeaderAndFooter {
        @Schema(description = "위젯들의 공개 여부", example = "[true, false, true, false, true, false, true, false, true, false, true, false, true]")
        @Size(min = 13, max = 13, message = "상태의 개수 12개")
        @JsonProperty("header_and_footer_status_list")
        private List<Boolean> headerAndFooterStatusList;
    }

}
