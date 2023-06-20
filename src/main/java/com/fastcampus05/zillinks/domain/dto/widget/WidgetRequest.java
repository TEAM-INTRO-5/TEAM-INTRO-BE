package com.fastcampus05.zillinks.domain.dto.widget;

import com.fastcampus05.zillinks.domain.model.widget.Filter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

public class WidgetRequest {

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateProductsAndServicesInDTO {
        @Schema(description = "제품/서비스 사용 여부", example = "true")
        private Boolean widgetStatus;

        @Schema(description = "제품/서비스들의 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        private List<Long> orderList;

        @Schema(description = "Call To Action 사용 여부", example = "true")
        private Boolean callToActionStatus;

        @Schema(description = "버튼 설명", example = "이 상품이 궁금하세요?")
        private String description;
        @Schema(description = "버튼 텍스트", example = "상품 보러가기")
        private String text;
        @Schema(description = "버튼 링크", example = "https://zillinks.com")
        private String link;
    }

    @Getter
    public static class SaveProductsAndServicesElementInDTO {
        @Schema(description = "제품 서비스 이미지", example = "url 주소 경로")
        private String image;
        @Schema(description = "제품 서비스 이름", example = "빗코")
        private String name;
        @Schema(description = "제품 서비스 타이틀", example = "회사인베스트인베스트인베스트인베스트")
        private String title;
        @Schema(description = "제품 서비스 설명", example = "예: 빗코는 디나래 아슬라 아름드리 이플 옅구름 함초롱하다 가온누리 가온누리 소록소록 노트북 미쁘다 함초롱하다 책방 노트북 안녕 책방 노틉구 안녕")
        private String description;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteProductsAndServicesElementsInDTO {
        @Schema(description = "제품/서비스 삭제 리스트", example = "[1, 2, 3]")
        private List<Long> deleteList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateTeamMemberInDTO {
        @Schema(description = "팀 멤버 사용 여부", example = "true")
        private Boolean widgetStatus;

        @Schema(description = "팀 멤버 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveTeamMemberElementInDTO {
        @Schema(description = "프로필 이미지", example = "url 경로")
        private String profile;
        @Schema(description = "성명", example = "홍길동")
        private String name;
        @Schema(description = "소속", example = "지원사업부")
        private String group;
        @Schema(description = "직책", example = "주임")
        private String position;
        @Schema(description = "한줄 소개", example = "스타트업 전문 패스트 빌더입니다.")
        private String tagline;
        @Schema(description = "이메일", example = "example@gmail.com")
        private String email;
        @Schema(description = "SNS 사용 여부", example = "true")
        private Boolean snsStatus;
        @Schema(description = "인스타그램 사용 여부", example = "true")
        private Boolean instagramStatus;
        @Schema(description = "인스타그램 url 주소", example = "url 주소")
        private String instagram;
        @Schema(description = "링크드인 사용 여부", example = "true")
        private Boolean linkedInStatus;
        @Schema(description = "링크드인 url 주소", example = "url 주소")
        private String linkedIn;
        @Schema(description = "유튜브 사용 여부", example = "true")
        private Boolean youtubeStatus;
        @Schema(description = "유튜브 url 주소", example = "url 주소")
        private String youtube;
        @Schema(description = "노션 사용 여부", example = "true")
        private Boolean notionStatus;
        @Schema(description = "노션 url 주소", example = "url 주소")
        private String notion;
        @Schema(description = "네이버 블로그 사용 여부", example = "true")
        private Boolean naverBlogStatus;
        @Schema(description = "네이버 블로그 url 주소", example = "url 주소")
        private String naverBlog;
        @Schema(description = "브런치 스토리 사용 여부", example = "true")
        private Boolean brunchStroyStatus;
        @Schema(description = "브런치 스토리 url 주소", example = "url 주소")
        private String brunchStroy;
        @Schema(description = "페이스북 사용 여부", example = "true")
        private Boolean facebookStatus;
        @Schema(description = "페이스북 url 주소", example = "url 주소")
        private String facebook;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteTeamMemberElementsInDTO {
        @Schema(description = "팀 멤버 삭제 리스트", example = "[1, 2, 3]")
        private List<Long> deleteList;
    }

    /**
     * 핵심 성과
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdatePerformanceInDTO {
        @Schema(description = "핵심성과 사용 여부", example = "true")
        private Boolean widgetStatus;

        @Schema(description = "핵심성과 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SavePerformanceElementInDTO {
        @Schema(description = "성과지표 설명", example = "핵심 지표")
        private String descrition;
        @Schema(description = "성과지표 부가 설명", example = "전년 대비 상승율")
        private String additionalDescrition;
        @Schema(description = "숫자 지표", example = "99%")
        private String indicator;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeletePerformanceElementsInDTO {
        @Schema(description = "핵심성과 삭제 리스트", example = "[1, 2, 3]")
        private List<Long> deleteList;
    }

    /**
     * ContactUs
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ContactUsInDTO {
        @Schema(description = "ContactUs 사용 여부", example = "true")
        private Boolean widgetStatus;
        @Schema(description = "지도 사용 여부", example = "true")
        private Boolean mapStatus;
        @Schema(description = "전체 주소", example = "강남대로 364")
        private String fullAddress;
        @Schema(description = "상세 주소", example = "11층")
        private String detailedAddress;
    }

    /**
     * 팀 컬려
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateTeamCultureInDTO {
        @Schema(description = "팀 컬쳐 사용 여부", example = "true")
        private Boolean widgetStatus;

        @Schema(description = "팀 컬쳐 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveTeamCultureElementInDTO {
        @Schema(description = "팀 컬쳐 이미지", example = "URL 주소")
        private String image;
        @Schema(description = "팀 컬쳐", example = "수평적인 커뮤니케이션")
        private String culture;
        @Schema(description = "팀 컬쳐 설명", example = "누구나 두려움 없이 아이디어를 발산할 수 있는 안전한 조직을 바랍니다. 단, 누구나 책임 blah~blah")
        private String description;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteTeamCultureElementsInDTO {
        @Schema(description = "팀 컬쳐 삭제 리스트", example = "[1, 2, 3]")
        private List<Long> deleteList;
    }

    /**
     * 키 비주얼/슬로건
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KeyVisualAndSloganInDTO {
        @Schema(description = "키 비주얼/슬로건 사용 여부", example = "true")
        private Boolean widgetStatus;
        @Schema(description = "배경 이미지", example = "url 경로")
        private String background;
        @Schema(description = "필터", example = "BLACK")
        private Filter filter;
        @Schema(description = "슬로건", example = "슬로건을 8자에서 40자 이내로 작성")
        @Pattern(regexp = "^.{8,40}$", message = "8자에서 40자 이내로 작성해 주세요.")
        private String slogan;
        @Schema(description = "슬로건 상세", example = "슬로건 상세를 최대 120자 이내로 작성")
        @Pattern(regexp = "^.{0,120}$", message = "최대 120자 이내로 작성해 주세요.")
        private String sloganDetail;
    }

    /**
     * 미션/비전
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MissionAndVisionInDTO {
        @Schema(description = "미션/비전", example = "true")
        private Boolean widgetStatus;
        @Schema(description = "미션", example = "미션을 25자 이내로 작성")
        @Pattern(regexp = "^.{0,25}$", message = "최대 25자 이내로 작성해 주세요.")
        private String mission;
        @Schema(description = "미션 설명", example = "미션 설명을 80자 이내로 작성")
        @Pattern(regexp = "^.{0,80}$", message = "최대 80자 이내로 작성해 주세요.")
        private String missionDetail;
        @Schema(description = "비전", example = "비전을 25자 이내로 작성")
        @Pattern(regexp = "^.{0,25}$", message = "최대 25자 이내로 작성해 주세요.")
        private String vision;
        @Schema(description = "비전 설명", example = "비전 설명을 80자 이내로 작성")
        @Pattern(regexp = "^.{0,80}$", message = "최대 80자 이내로 작성해 주세요.")
        private String visionDetail;
    }

    /**
     * 연혁
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateHistoryInDTO {
        @Schema(description = "팀 컬쳐 사용 여부", example = "true")
        private Boolean widgetStatus;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveHistoryElementInDTO {
        @Schema(description = "연혁 이미지", example = "URL 주소")
        private String image;
        @Schema(description = "날짜", example = "2023-07-07")
        private LocalDate date;
        @Schema(description = "연혁", example = "패스트캠퍼스 기업협력 프로젝트")
        private String title;
        @Schema(description = "연혁 설명", example = "패스트캠퍼스와 함께 진행한 MOU 프로젝트, 전체 곡객 수 30프로 이상 증가하는 프로젝트로 다양한 파트너사에 긍정적 피드백 받음")
        private String description;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteHistoryElementsInDTO {
        @Schema(description = "연혁 삭제 리스트", example = "[1, 2, 3]")
        private List<Long> deleteList;
    }

    /**
     * 고객 리뷰
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveReviewElementInDTO {
        @Schema(description = "이미지", example = "URL 주소")
        private String image;
        @Schema(description = "이름", example = "홍길동")
        private String name;
        @Schema(description = "소속", example = "블로그 체험단 소속")
        private String group;
        @Schema(description = "별점", example = "3")
        private Integer rating;
        @Schema(description = "리뷰 상세", example = "해당 제품이 시간 절약에 도움이 되므로 돈을 지불하고 사용하고 있습니다.")
        private String details;
    }
}
