package com.fastcampus05.zillinks.domain.dto.widget;

import com.fastcampus05.zillinks.domain.model.widget.Filter;
import com.fastcampus05.zillinks.domain.model.widget.PartnersType;
import com.fastcampus05.zillinks.domain.model.widget.PatentType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class WidgetRequest {

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateProductsAndServicesInDTO {
        @Schema(description = "제품/서비스 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;

        @Schema(description = "제품/서비스들의 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        @NotNull(message = "order_list 필드를 비워둘 순 없습니다.")
        private List<Long> orderList;

        @Schema(description = "Call To Action 사용 여부", example = "true")
        @NotNull(message = "call_to_action_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean callToActionStatus;

        @Schema(description = "버튼 설명", example = "이 상품이 궁금하세요?")
        @Size(max = 30, message = "버튼 설명은 최대 30자까지 입력 가능합니다.")
        private String description;
        @Schema(description = "버튼 텍스트", example = "상품 보러가기")
        @Size(max = 8, message = "버튼 텍스트는 최대 8자까지 입력 가능합니다.")
        private String text;
        @Schema(description = "버튼 링크", example = "https://zillinks.com")
        @Size(max = 150, message = "버튼 링크는 최대 150자까지 입력 가능합니다.")
        private String link;
    }

    @Getter
    public static class SaveProductsAndServicesElementInDTO {
        @Schema(description = "제품 서비스 이미지", example = "url 주소 경로")
        private String image;
        @Schema(description = "제품 서비스 이름", example = "빗코")
        @Size(max = 12, message = "제품 서비스 이름은 최대 12자까지 입력 가능합니다.")
        private String name;
        @Schema(description = "제품 서비스 타이틀", example = "회사인베스트인베스트인베스트인베스트")
        @Size(max = 20, message = "제품 서비스 타이틀은 최대 20자까지 입력 가능합니다.")
        private String title;
        @Schema(description = "제품 서비스 설명", example = "예: 빗코는 디나래 아슬라 아름드리 이플 옅구름 함초롱하다 가온누리 가온누리 소록소록 노트북 미쁘다 함초롱하다 책방 노트북 안녕 책방 노틉구 안녕")
        @Size(max = 80, message = "제품 서비스 설명은 최대 80자까지 입력 가능합니다.")
        private String description;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteProductsAndServicesElementsInDTO {
        @Schema(description = "제품/서비스 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateTeamMemberInDTO {
        @Schema(description = "팀 멤버 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;

        @Schema(description = "팀 멤버 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        @NotNull(message = "order_list 필드를 비워둘 순 없습니다.")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveTeamMemberElementInDTO {
        @Schema(description = "프로필 이미지", example = "url 경로")
        private String profile;
        @Schema(description = "성명", example = "홍길동")
        @NotNull
        private String name;
        @Schema(description = "소속", example = "지원사업부")
        @NotNull
        private String group;
        @Schema(description = "직책", example = "주임")
        @NotNull
        private String position;
        @Schema(description = "한줄 소개", example = "스타트업 전문 패스트 빌더입니다.")
        @NotNull
        private String tagline;
        @Schema(description = "이메일", example = "example@gmail.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
        @Schema(description = "SNS 사용 여부", example = "true")
        @NotNull
        private Boolean snsStatus;
        @Schema(description = "인스타그램 사용 여부", example = "true")
        @NotNull
        private Boolean instagramStatus;
        @Schema(description = "인스타그램 url 주소", example = "url 주소")
        private String instagram;
        @Schema(description = "링크드인 사용 여부", example = "true")
        @NotNull
        private Boolean linkedInStatus;
        @Schema(description = "링크드인 url 주소", example = "url 주소")
        private String linkedIn;
        @Schema(description = "유튜브 사용 여부", example = "true")
        @NotNull
        private Boolean youtubeStatus;
        @Schema(description = "유튜브 url 주소", example = "url 주소")
        private String youtube;
        @Schema(description = "노션 사용 여부", example = "true")
        @NotNull
        private Boolean notionStatus;
        @Schema(description = "노션 url 주소", example = "url 주소")
        private String notion;
        @Schema(description = "네이버 블로그 사용 여부", example = "true")
        @NotNull
        private Boolean naverBlogStatus;
        @Schema(description = "네이버 블로그 url 주소", example = "url 주소")
        private String naverBlog;
        @Schema(description = "브런치 스토리 사용 여부", example = "true")
        @NotNull
        private Boolean brunchStroyStatus;
        @Schema(description = "브런치 스토리 url 주소", example = "url 주소")
        private String brunchStroy;
        @Schema(description = "페이스북 사용 여부", example = "true")
        @NotNull
        private Boolean facebookStatus;
        @Schema(description = "페이스북 url 주소", example = "url 주소")
        private String facebook;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteTeamMemberElementsInDTO {
        @Schema(description = "팀 멤버 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    /**
     * 핵심 성과
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdatePerformanceInDTO {
        @Schema(description = "핵심성과 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;

        @Schema(description = "핵심성과 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        @NotNull(message = "order_list 필드를 비워둘 순 없습니다.")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SavePerformanceElementInDTO {
        @Schema(description = "성과지표 설명", example = "핵심 지표")
        @NotNull
        @Size(max = 12)
        private String descrition;
        @Schema(description = "성과지표 부가 설명", example = "전년 대비 상승율")
        @NotNull
        @Size(max = 30)
        private String additionalDescrition;
        @Schema(description = "숫자 지표", example = "99%")
        @Pattern(regexp = "^\\d+(\\.\\d{1,2})?%$", message = "지표는 숫자와 % 기호만 포함해야 합니다.")
        private String indicator;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeletePerformanceElementsInDTO {
        @Schema(description = "핵심성과 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    /**
     * ContactUs
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ContactUsInDTO {
        @Schema(description = "ContactUs 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;
        @Schema(description = "지도 사용 여부", example = "true")
        @NotNull
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
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;

        @Schema(description = "팀 컬쳐 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        @NotNull(message = "order_list 필드를 비워둘 순 없습니다.")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveTeamCultureElementInDTO {
        @Schema(description = "팀 컬쳐 이미지", example = "URL 주소")
        private String image;
        @Schema(description = "팀 컬쳐", example = "수평적인 커뮤니케이션")
        @NotNull
        @Size(max = 20)
        private String culture;
        @Schema(description = "팀 컬쳐 설명", example = "누구나 두려움 없이 아이디어를 발산할 수 있는 안전한 조직을 바랍니다. 단, 누구나 책임 blah~blah")
        @NotNull
        @Size(max = 200)
        private String description;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteTeamCultureElementsInDTO {
        @Schema(description = "팀 컬쳐 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    /**
     * 키 비주얼/슬로건
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KeyVisualAndSloganInDTO {
        @Schema(description = "키 비주얼/슬로건 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
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
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
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
        @Schema(description = "연혁 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveHistoryElementInDTO {
        @Schema(description = "연혁 이미지", example = "URL 주소")
        private String image;
        @Schema(description = "날짜", example = "2023-07-07")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜 형식이 올바르지 않습니다. (예: '2023-07-07')")
        @NotNull
        @Size(max = 20)
        private LocalDate date;
        @Schema(description = "연혁", example = "패스트캠퍼스 기업협력 프로젝트")
        @NotNull
        @Size(max = 30)
        private String title;
        @Schema(description = "연혁 설명", example = "패스트캠퍼스와 함께 진행한 MOU 프로젝트, 전체 곡객 수 30프로 이상 증가하는 프로젝트로 다양한 파트너사에 긍정적 피드백 받음")
        @NotNull
        @Size(max = 80)
        private String description;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteHistoryElementsInDTO {
        @Schema(description = "연혁 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    /**
     * 고객 리뷰
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateReviewInDTO {
        @Schema(description = "고객 리뷰 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;

        @Schema(description = "고객 리뷰 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        @NotNull(message = "order_list 필드를 비워둘 순 없습니다.")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveReviewElementInDTO {
        @Schema(description = "이미지", example = "URL 주소")
        private String image;
        @Schema(description = "이름", example = "홍길동")
        @NotNull
        @Size(max = 12)
        private String name;
        @Schema(description = "소속", example = "블로그 체험단 소속")
        @NotNull
        @Size(max = 12)
        private String group;
        @Schema(description = "별점", example = "3")
        @NotNull
        private Integer rating;
        @Schema(description = "리뷰 상세", example = "해당 제품이 시간 절약에 도움이 되므로 돈을 지불하고 사용하고 있습니다.")
        @NotNull
        @Size(max = 100)
        private String details;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteReviewElementsInDTO {
        @Schema(description = "고객 리뷰 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    /**
     * 인증/특허
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdatePatentInDTO {
        @Schema(description = "인증/특허 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;

        @Schema(description = "인증/특허 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        @NotNull(message = "order_list 필드를 비워둘 순 없습니다.")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SavePatentElementInDTO {
        @Schema(description = "구분", example = "PATENT")
        @NotNull
        private PatentType patentType;
        @Schema(description = "특허/인증 이름", example = "특허/인증 발급 정보")
        @NotNull
        @Size(max = 12)
        private String title;
        @Schema(description = "특허/인증 이미지", example = "URL 주소")
        private String image;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeletePatentElementsInDTO {
        @Schema(description = "특허/인증 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    /**
     * 보도 자료
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateNewsInDTO {
        @Schema(description = "보도 자료 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;

        @Schema(description = "보도 자료 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        @NotNull(message = "order_list 필드를 비워둘 순 없습니다.")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveNewsElementInDTO {
        @Schema(description = "보도자료 이미지", example = "url 경로")
        @NotNull
        private String image;
        @Schema(description = "보도날짜", example = "2023-06-07")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜 형식이 올바르지 않습니다. (예: '2023-07-07')")
        @NotNull
        private LocalDate date;
        @Schema(description = "보도매체", example = "중앙일보")
        @NotNull
        private String press;
        @Schema(description = "기사 제목", example = "기사 제목")
        @NotNull
        private String title;
        @Schema(description = "기사 설명", example = "##년도 ##컨퍼런스")
        @NotNull
        @Size(max = 100)
        private String description;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteNewsElementsInDTO {
        @Schema(description = "보도 자료 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    /**
     * 파트너스
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdatePartnersInDTO {
        @Schema(description = "파트너스 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;

        @Schema(description = "파트너스 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        @NotNull(message = "order_list 필드를 비워둘 순 없습니다.")
        private List<Long> orderList;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SavePartnersElementInDTO {
        @Schema(description = "구분", example = "PARTNERS")
        @NotNull
        private PartnersType partnersType;
        @Schema(description = "회사 이름", example = "질링스")
        @NotNull
        @Size(max = 40)
        private String companyName;
        @Schema(description = "회사 로고", example = "url 주소")
        private String logo;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeletePartnersElementsInDTO {
        @Schema(description = "파트너스 삭제 리스트", example = "[1, 2, 3]")
        @NotNull(message = "delete_list 필드를 비워둘 순 없습니다.")
        private List<Long> deleteList;
    }

    /**
     * 다운로드
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DownloadInDTO {
        @Schema(description = "다운로드 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;
        @Schema(description = "설명", example = "DOWNLOAD / 자세한 소개")
        @Pattern(regexp = "^.{0,30}$", message = "최대 30자 이내로 작성해 주세요.")
        private String description;
        @Schema(description = "미디어 키트", example = "url 주소 경로")
        private String mediaKitFile;
        @Schema(description = "회사소개서", example = "url 주소 경로")
        private String introFile;
    }

    /**
     * 채널
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ChannelInDTO {
        @Schema(description = "채널 사용 여부", example = "true")
        @NotNull(message = "widget_status 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean widgetStatus;
        @Schema(description = "인스타그램 사용 여부", example = "true")
        @NotNull
        private Boolean instagramStatus;
        @Schema(description = "인스타그램 아이디", example = "instagram_zillinks")
        private String instagram;
        @Schema(description = "링크드인 사용 여부", example = "true")
        @NotNull
        private Boolean linkedInStatus;
        @Schema(description = "링크드인 아이디", example = "linkedIn_zillinks")
        private String linkedIn;
        @Schema(description = "유튜브 사용 여부", example = "true")
        @NotNull
        private Boolean youtubeStatus;
        @Schema(description = "유튜브 아이디", example = "youtube_zillinks")
        private String youtube;
        @Schema(description = "노션 사용 여부", example = "true")
        @NotNull
        private Boolean notionStatus;
        @Schema(description = "노션 아이디", example = "notion_zillinks")
        private String notion;
        @Schema(description = "네이버블로그 사용 여부", example = "true")
        @NotNull
        private Boolean naverBlogStatus;
        @Schema(description = "네이버블로그 아이디", example = "naverBlog_zillinks")
        private String naverBlog;
        @Schema(description = "브런치스토리 사용 여부", example = "true")
        @NotNull
        private Boolean brunchStroyStatus;
        @Schema(description = "브런치스토리 아이디", example = "brunchStroy_zillinks")
        private String brunchStroy;
        @Schema(description = "페이스북 사용 여부", example = "true")
        @NotNull
        private Boolean facebookStatus;
        @Schema(description = "페이스북 아이디", example = "facebook_zillinks")
        private String facebook;
    }
}
