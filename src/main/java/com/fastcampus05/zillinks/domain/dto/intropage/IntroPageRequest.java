package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.ThemeType;
import com.fastcampus05.zillinks.domain.model.widget.WidgetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class IntroPageRequest {

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindIntroPageInDTO {
        @Schema(description = "기본 주소", example = "zillinks")
        @Pattern(regexp = "^[A-Za-z0-9]{1,20}$", message = "서브 도메인은 1자리에서 20자리 사이의 숫자와 문자의 조합이어야 합니다.")
        private String subDomain;
        @Schema(description = "검색 키워드", example = "질링스 회사소개")
        @Size(max = 100, message = "검색 키워드는 최대 100자까지 입력 가능합니다.")
        private String keyword;
        @Schema(description = "공유 번호", example = "_code")
        @Size(max = 100, message = "공유 번호 최대 100자까지 입력 가능합니다.")
        private String share;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveIntroPageInDTO {
        @Schema(description = "사용되는 위젯 리스트 순서대로 기입", example = "[MISSIONANDVISION, PRODUCTSANDSERVICES, CONTACTUS, REVIEW]")
        @NotNull(message = "widget_type_list 필드를 비워둘 순 없습니다.")
        private List<WidgetType> widgetTypeList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateInDTO {
        @Schema(description = "공개 여부", example = "false")
        @NotNull(message = "status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean status;

        @Schema(description = "위젯들의 공개 여부", example = "[true, false, true, false, true, false, true, false, true, false, true, false, true, false]")
        @Size(min = 14, max = 14, message = "위젯의 개수 14개")
        private List<Boolean> widgetStatusList;

        @Schema(description = "위젯들의 순서 리스트", example = "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]")
        @Size(min = 14, max = 14, message = "위젯의 개수 14개")
        private List<Integer> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateThemeInDTO {
        @Schema(description = "테마 선택", example = "ThemeA")
        private ThemeType themeType;

        @Schema(description = "기본 색상 변경", example = "#4B48DF")
        @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$")
        private String color;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateSiteInfoInDTO {

        @Schema(description = "pavicon 저장 경로", example = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg")
        private String pavicon; // 경로

        @Schema(description = "서브도메인 설정", example = "zillinks")
        @Pattern(regexp = "^[A-Za-z0-9]{1,20}$", message = "서브 도메인은 1자리에서 20자리 사이의 숫자와 문자의 조합이어야 합니다.")
        private String subDomain;

        @Schema(description = "사이트 이름 ", example = "질링스")
        @Size(max = 40, message = "사이트 이름은 최대 40자까지 입력 가능합니다.")
        private String title;

        @Schema(description = "사이트 설명", example = "질링스 짱짱맨")
        @Size(max = 80, message = "사이트 설명은 최대 40자까지 입력 가능합니다.")
        private String description;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateCompanyInfoInDTO {
        @Schema(description = "대표 성명", example = "홍길동")
        @Size(max = 10, message = "대표 성명은 최대 10자까지 입력 가능합니다.")
        private String representative;
        @Schema(description = "회사 로고", example = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg")
        private String logo;
        @Schema(description = "연락용 이메일", example = "taeheoki@naver.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String contactEmail;
        @Schema(description = "전화 번호", example = "000-0000-0000")
        @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화 번호 형식은 000-0000-0000이어야 합니다.")
        private String phoneNumber;
        @Schema(description = "팩스 번호", example = "000-0000-0000")
        @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "팩스 번호 형식은 000-0000-0000이어야 합니다.")
        private String faxNumber;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateHeaderAndFooter {
        @Schema(description = "위젯들의 공개 여부", example = "[true, false, true, false, true, false, true, false, true, false, true, false, true]")
        @Size(min = 13, max = 13, message = "상태의 개수 12개")
        private List<Boolean> headerAndFooterStatusList;
    }

}
