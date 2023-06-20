package com.fastcampus05.zillinks.domain.dto.widget;

import com.fastcampus05.zillinks.domain.model.widget.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class WidgetResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveProductsAndServicesElementOutDTO {
        private Long productsAndServicesElementId;
        private String image;
        private String name;
        private String title;
        private String description;

        public static SaveProductsAndServicesElementOutDTO toOutDTO(
                ProductsAndServicesElement productsAndServicesElement
        ) {
            return SaveProductsAndServicesElementOutDTO.builder()
                    .productsAndServicesElementId(productsAndServicesElement.getId())
                    .image(productsAndServicesElement.getImage())
                    .name(productsAndServicesElement.getName())
                    .title(productsAndServicesElement.getTitle())
                    .description(productsAndServicesElement.getDescription())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateProductsAndServicesOutDTO {
        private Long productsAndServicesId;
        //        private List<Long> orderList;
        private List<ProductsAndServicesElementOutDTO> productsAndServicesElements;
        private Boolean callToActionStatus;
        private CallToActionOutDTO callToAction;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class ProductsAndServicesElementOutDTO {
            private Long productsAndServicesElementId;
            private String image;
            private String name;
            private String title;
            private String description;

            private static ProductsAndServicesElementOutDTO toOutDTO(ProductsAndServicesElement productsAndServicesElement) {
                return ProductsAndServicesElementOutDTO.builder()
                        .productsAndServicesElementId(productsAndServicesElement.getId())
                        .image(productsAndServicesElement.getImage())
                        .name(productsAndServicesElement.getName())
                        .title(productsAndServicesElement.getTitle())
                        .description(productsAndServicesElement.getDescription())
                        .build();
            }
        }

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class CallToActionOutDTO {
            private String description;
            private String text;
            private String link;
        }

        public static UpdateProductsAndServicesOutDTO toOutDTO(
                ProductsAndServices productsAndServices
        ) {
            List<ProductsAndServicesElement> productsAndServicesElements = productsAndServices.getProductsAndServicesElements();
            List<ProductsAndServicesElementOutDTO> productsAndServicesElementOutDTOs = new ArrayList<>();
            for (int i = 0; i < productsAndServicesElements.size(); i++) {
                for (ProductsAndServicesElement productsAndServicesElement : productsAndServicesElements) {
                    if (productsAndServicesElement.getOrder() != i + 1)
                        continue;
                    productsAndServicesElementOutDTOs.add(ProductsAndServicesElementOutDTO.toOutDTO(productsAndServicesElement));
                }
            }
            return UpdateProductsAndServicesOutDTO.builder()
                    .productsAndServicesId(productsAndServices.getId())
                    .productsAndServicesElements(productsAndServicesElementOutDTOs)
                    .callToActionStatus(productsAndServices.getCallToActionStatus())
                    .callToAction(productsAndServices.getCallToActionStatus() ? CallToActionOutDTO.builder()
                            .description(productsAndServices.getCallToAction().getDescription())
                            .text(productsAndServices.getCallToAction().getText())
                            .link(productsAndServices.getCallToAction().getLink())
                            .build() : null)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateTeamMemberOutDTO {
        private Long teamMemberId;
        private List<TeamMemberElementOutDTO> teamMemberElements;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class TeamMemberElementOutDTO {
            private Long teamMemberElementId;
            private String profile;
            private String name;
            private String group;
            private String position;
            private String tagline;
            private String email;
            private Boolean snsStatus;
            private SnsListOutDTO snsList;

            @Getter
            @Builder
            @AllArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            public static class SnsListOutDTO {
                private Boolean instagramStatus;
                private String instagram;
                private Boolean linkedInStatus;
                private String linkedIn;
                private Boolean youtubeStatus;
                private String youtube;
                private Boolean notionStatus;
                private String notion;
                private Boolean naverBlogStatus;
                private String naverBlog;
                private Boolean brunchStroyStatus;
                private String brunchStroy;
                private Boolean facebookStatus;
                private String facebook;
            }

            private static TeamMemberElementOutDTO toOutDTO(TeamMemberElement teamMemberElement) {
                return TeamMemberElementOutDTO.builder()
                        .teamMemberElementId(teamMemberElement.getId())
                        .profile(teamMemberElement.getProfile())
                        .name(teamMemberElement.getName())
                        .group(teamMemberElement.getGroup())
                        .position(teamMemberElement.getPosition())
                        .tagline(teamMemberElement.getTagline())
                        .email(teamMemberElement.getEmail())
                        .snsStatus(teamMemberElement.getSnsStatus())
                        .snsList(teamMemberElement.getSnsList() != null ? SnsListOutDTO.builder()
                                .instagramStatus(teamMemberElement.getSnsList().getInstagramStatus())
                                .instagram(teamMemberElement.getSnsList().getInstagram())
                                .linkedInStatus(teamMemberElement.getSnsList().getLinkedInStatus())
                                .linkedIn(teamMemberElement.getSnsList().getLinkedIn())
                                .youtubeStatus(teamMemberElement.getSnsList().getYoutubeStatus())
                                .youtube(teamMemberElement.getSnsList().getYoutube())
                                .notionStatus(teamMemberElement.getSnsList().getNotionStatus())
                                .notion(teamMemberElement.getSnsList().getNotion())
                                .naverBlogStatus(teamMemberElement.getSnsList().getNaverBlogStatus())
                                .naverBlog(teamMemberElement.getSnsList().getNaverBlog())
                                .brunchStroyStatus(teamMemberElement.getSnsList().getBrunchStroyStatus())
                                .brunchStroy(teamMemberElement.getSnsList().getBrunchStroy())
                                .facebookStatus(teamMemberElement.getSnsList().getFacebookStatus())
                                .facebook(teamMemberElement.getSnsList().getFacebook())
                                .build() : null)
                        .build();
            }
        }

        public static UpdateTeamMemberOutDTO toOutDTO(
                TeamMember teamMember
        ) {
            List<TeamMemberElement> teamMemberElements = teamMember.getTeamMemberElements();
            List<TeamMemberElementOutDTO> teamMemberElementOutDTOs = new ArrayList<>();
            for (int i = 0; i < teamMemberElements.size(); i++) {
                for (TeamMemberElement teamMemberElement : teamMemberElements) {
                    if (teamMemberElement.getOrder() != i + 1)
                        continue;
                    teamMemberElementOutDTOs.add(TeamMemberElementOutDTO.toOutDTO(teamMemberElement));
                }
            }
            return UpdateTeamMemberOutDTO.builder()
                    .teamMemberId(teamMember.getId())
                    .teamMemberElements(teamMemberElementOutDTOs)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveTeamMemberElementOutDTO {
        private Long teamMemberElementId;
        private String profile;
        private String name;
        private String group;
        private String position;
        private String tagline;
        private String email;
        private Boolean snsStatus;
        private SnsListOutDTO snsList;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class SnsListOutDTO {
            private Boolean instagramStatus;
            private String instagram;
            private Boolean linkedInStatus;
            private String linkedIn;
            private Boolean youtubeStatus;
            private String youtube;
            private Boolean notionStatus;
            private String notion;
            private Boolean naverBlogStatus;
            private String naverBlog;
            private Boolean brunchStroyStatus;
            private String brunchStroy;
            private Boolean facebookStatus;
            private String facebook;
        }

        public static SaveTeamMemberElementOutDTO toOutDTO(
                TeamMemberElement teamMemberElement
        ) {
            return SaveTeamMemberElementOutDTO.builder()
                    .teamMemberElementId(teamMemberElement.getId())
                    .profile(teamMemberElement.getProfile())
                    .name(teamMemberElement.getName())
                    .group(teamMemberElement.getGroup())
                    .position(teamMemberElement.getPosition())
                    .tagline(teamMemberElement.getTagline())
                    .email(teamMemberElement.getEmail())
                    .snsStatus(teamMemberElement.getSnsStatus())
                    .snsList(teamMemberElement.getSnsStatus() ? SnsListOutDTO.builder()
                            .instagramStatus(teamMemberElement.getSnsList().getInstagramStatus())
                            .instagram(teamMemberElement.getSnsList().getInstagram())
                            .linkedInStatus(teamMemberElement.getSnsList().getLinkedInStatus())
                            .linkedIn(teamMemberElement.getSnsList().getLinkedIn())
                            .youtubeStatus(teamMemberElement.getSnsList().getYoutubeStatus())
                            .youtube(teamMemberElement.getSnsList().getYoutube())
                            .notionStatus(teamMemberElement.getSnsList().getNotionStatus())
                            .notion(teamMemberElement.getSnsList().getNotion())
                            .naverBlogStatus(teamMemberElement.getSnsList().getNaverBlogStatus())
                            .naverBlog(teamMemberElement.getSnsList().getNaverBlog())
                            .brunchStroyStatus(teamMemberElement.getSnsList().getBrunchStroyStatus())
                            .brunchStroy(teamMemberElement.getSnsList().getBrunchStroy())
                            .facebookStatus(teamMemberElement.getSnsList().getFacebookStatus())
                            .facebook(teamMemberElement.getSnsList().getFacebook())
                            .build() : null)
                    .build();
        }
    }

    /**
     * 핵심 성과
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdatePerformanceOutDTO {
        private Long performanceId;
        private List<PerformanceElementOutDTO> performanceElements;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class PerformanceElementOutDTO {
            private Long performanceElementId;
            private String descrition;
            private String additionalDescrition;
            private String indicator;

            private static PerformanceElementOutDTO toOutDTO(PerformanceElement performanceElement) {
                return PerformanceElementOutDTO.builder()
                        .performanceElementId(performanceElement.getId())
                        .descrition(performanceElement.getDescrition())
                        .additionalDescrition(performanceElement.getAdditionalDescrition())
                        .indicator(performanceElement.getIndicator())
                        .build();
            }
        }

        public static UpdatePerformanceOutDTO toOutDTO(Performance performance) {
            List<PerformanceElement> performanceElements = performance.getPerformanceElements();
            List<PerformanceElementOutDTO> performanceElementOutDTOs = new ArrayList<>();
            for (int i = 0; i < performanceElements.size(); i++) {
                for (PerformanceElement performanceElement : performanceElements) {
                    if (performanceElement.getOrder() != i + 1)
                        continue;
                    performanceElementOutDTOs.add(PerformanceElementOutDTO.toOutDTO(performanceElement));
                }
            }
            return UpdatePerformanceOutDTO.builder()
                    .performanceId(performance.getId())
                    .performanceElements(performanceElementOutDTOs)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SavePerformanceElementOutDTO {
        private Long performanceElementId;
        private String descrition;
        private String additionalDescrition;
        private String indicator;

        public static SavePerformanceElementOutDTO toOutDTO(
                PerformanceElement performanceElement
        ) {
            return SavePerformanceElementOutDTO.builder()
                    .performanceElementId(performanceElement.getId())
                    .descrition(performanceElement.getDescrition())
                    .additionalDescrition(performanceElement.getAdditionalDescrition())
                    .indicator(performanceElement.getIndicator())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include. NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ContactUsOutDTO {
        private Boolean mapStatus;
        private String fullAddress;
        private String detailedAddress;
        private String latitude; // 위도
        private String longitude; // 경도

        public static ContactUsOutDTO toOutDTO(ContactUs contactUs) {
            return ContactUsOutDTO.builder()
                    .mapStatus(contactUs.getMapStatus())
                    .fullAddress(contactUs.getFullAddress())
                    .detailedAddress(contactUs.getDetailedAddress())
                    .latitude(contactUs.getLatitude())
                    .longitude(contactUs.getLongitude())
                    .build();
        }
    }

    /**
     * 팀 컬쳐
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateTeamCultureOutDTO {
        private Long teamCultureId;
        private List<TeamCultureElementOutDTO> teamCultureElements;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class TeamCultureElementOutDTO {
            private Long teamCultureElementId;
            private String image;
            private String culture;
            private String desciption;

            private static TeamCultureElementOutDTO toOutDTO(TeamCultureElement teamCultureElement) {
                return TeamCultureElementOutDTO.builder()
                        .teamCultureElementId(teamCultureElement.getId())
                        .image(teamCultureElement.getImage())
                        .culture(teamCultureElement.getCulture())
                        .desciption(teamCultureElement.getDesciption())
                        .build();
            }
        }

        public static UpdateTeamCultureOutDTO toOutDTO(TeamCulture teamCulture) {
            List<TeamCultureElement> teamCultureElements = teamCulture.getTeamCultureElements();
            List<TeamCultureElementOutDTO> teamCultureElementOutDTOs = new ArrayList<>();
            for (int i = 0; i < teamCultureElements.size(); i++) {
                for (TeamCultureElement teamCultureElement : teamCultureElements) {
                    if (teamCultureElement.getOrder() != i + 1)
                        continue;
                    teamCultureElementOutDTOs.add(TeamCultureElementOutDTO.toOutDTO(teamCultureElement));
                }
            }
            return UpdateTeamCultureOutDTO.builder()
                    .teamCultureId(teamCulture.getId())
                    .teamCultureElements(teamCultureElementOutDTOs)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveTeamCultureElementOutDTO {
        private Long teamCultureElementId;
        private String image;
        private String culture;
        private String desciption;

        public static SaveTeamCultureElementOutDTO toOutDTO(
                TeamCultureElement teamCultureElement
        ) {
            return SaveTeamCultureElementOutDTO.builder()
                    .teamCultureElementId(teamCultureElement.getId())
                    .image(teamCultureElement.getImage())
                    .culture(teamCultureElement.getCulture())
                    .desciption(teamCultureElement.getDesciption())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KeyVisualAndSloganOutDTO {
        private String background;
        private Filter filter;
        private String slogan;
        private String sloganDetail;

        public static KeyVisualAndSloganOutDTO toOutDTO(
                KeyVisualAndSlogan keyVisualAndSlogan
        ) {
            return KeyVisualAndSloganOutDTO.builder()
                    .background(keyVisualAndSlogan.getBackground())
                    .filter(keyVisualAndSlogan.getFilter())
                    .slogan(keyVisualAndSlogan.getSlogan())
                    .sloganDetail(keyVisualAndSlogan.getSloganDetail())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MissionAndVisionOutDTO {
        private String mission;
        private String missionDetail;
        private String vision;
        private String visionDetail;

        public static MissionAndVisionOutDTO toOutDTO(
                MissionAndVision keyVisualAndSlogan
        ) {
            return MissionAndVisionOutDTO.builder()
                    .mission(keyVisualAndSlogan.getMission())
                    .missionDetail(keyVisualAndSlogan.getMissionDetail())
                    .vision(keyVisualAndSlogan.getVision())
                    .visionDetail(keyVisualAndSlogan.getVisionDetail())
                    .build();
        }
    }

    /**
     * 연혁
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateHistoryOutDTO {
        private Long historyId;
        private List<HistoryElementOutDTO> histroyElements;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class HistoryElementOutDTO {
            private Long historyElementId;
            private String image;
            private LocalDate date;
            private String title;
            private String description;

            private static HistoryElementOutDTO toOutDTO(HistoryElement historyElement) {
                return HistoryElementOutDTO.builder()
                        .historyElementId(historyElement.getId())
                        .image(historyElement.getImage())
                        .date(historyElement.getDate())
                        .title(historyElement.getImage())
                        .description(historyElement.getDescription())
                        .build();
            }
        }

        public static UpdateHistoryOutDTO toOutDTO(History history) {
            List<HistoryElement> historyElements = history.getHistoryElements();
            Collections.sort(historyElements, Collections.reverseOrder());
            List<HistoryElementOutDTO> historyElementOutDTOs = new ArrayList<>();
            for (HistoryElement historyElement : historyElements) {
                historyElementOutDTOs.add(HistoryElementOutDTO.toOutDTO(historyElement));
            }
            return UpdateHistoryOutDTO.builder()
                    .historyId(history.getId())
                    .histroyElements(historyElementOutDTOs)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveHistoryElementOutDTO {
        private Long historyElementId;
        private String image;
        private LocalDate date;
        private String title;
        private String description;

        public static SaveHistoryElementOutDTO toOutDTO(
                HistoryElement historyElement
        ) {
            return SaveHistoryElementOutDTO.builder()
                    .historyElementId(historyElement.getId())
                    .image(historyElement.getImage())
                    .date(historyElement.getDate())
                    .title(historyElement.getTitle())
                    .description(historyElement.getDescription())
                    .build();
        }
    }

    /**
     * 고객 리뷰
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateReviewOutDTO {
        private Long reviewId;
        private List<ReviewElementOutDTO> reviewElements;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class ReviewElementOutDTO {
            private Long reviewElementId;
            private String image;
            private String name;
            private String group;
            private Integer rating;
            private String details;

            private static ReviewElementOutDTO toOutDTO(ReviewElement reviewElement) {
                return ReviewElementOutDTO.builder()
                        .reviewElementId(reviewElement.getId())
                        .image(reviewElement.getImage())
                        .name(reviewElement.getName())
                        .group(reviewElement.getGroup())
                        .rating(reviewElement.getRating())
                        .details(reviewElement.getDetails())
                        .build();
            }
        }

        public static UpdateReviewOutDTO toOutDTO(Review review) {
            List<ReviewElement> reviewElements = review.getReviewElement();
            List<ReviewElementOutDTO> reviewElementOutDTOs = new ArrayList<>();
            for (int i = 0; i < reviewElements.size(); i++) {
                for (ReviewElement reviewElement : reviewElements) {
                    if (reviewElement.getOrder() != i + 1)
                        continue;
                    reviewElementOutDTOs.add(ReviewElementOutDTO.toOutDTO(reviewElement));
                }
            }
            return UpdateReviewOutDTO.builder()
                    .reviewId(review.getId())
                    .reviewElements(reviewElementOutDTOs)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveReviewElementOutDTO {
        private Long reviewElementId;
        private String image;
        private String name;
        private String group;
        private Integer rating;
        private String details;

        public static SaveReviewElementOutDTO toOutDTO(
                ReviewElement reviewElement
        ) {
            return SaveReviewElementOutDTO.builder()
                    .reviewElementId(reviewElement.getId())
                    .image(reviewElement.getImage())
                    .name(reviewElement.getName())
                    .group(reviewElement.getGroup())
                    .rating(reviewElement.getRating())
                    .details(reviewElement.getDetails())
                    .build();
        }
    }

    /**
     * 특허/인증
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SavePatentElementOutDTO {
        private Long patentElementId;
        private PatentType patentType;
        private String title;
        private String image;

        public static SavePatentElementOutDTO toOutDTO(
                PatentElement patentElement
        ) {
            return SavePatentElementOutDTO.builder()
                    .patentElementId(patentElement.getId())
                    .patentType(patentElement.getPatentType())
                    .title(patentElement.getTitle())
                    .image(patentElement.getImage())
                    .build();
        }
    }
}
