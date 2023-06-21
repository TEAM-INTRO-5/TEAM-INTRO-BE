package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPageStatus;
import com.fastcampus05.zillinks.domain.model.widget.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class IntroPageResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class IntroPageOutDTO {
        private Long introPageId;
        private IntroPageStatus introPageStatus;
        private ThemeOutDTO theme;
        private CompanyInfoOutDTO companyInfo;
        private SiteInfoOutDTO siteInfo;
        private HeaderAndFooterOutDTO headerAndFooter;
        private List<WidgetOutDTO> widgets;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class ThemeOutDTO {
            private String type;
            private String color;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class CompanyInfoOutDTO {
            private String companyName;
            private String startDate;
            private String representative;
            private String logo;
            private String contactEmail;
            private String bizNum;
            private String phoneNumber;
            private String faxNumber;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class SiteInfoOutDTO {
            private String pavicon; // 경로
            private String subDomain;
            private String title;
            private String description;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class HeaderAndFooterOutDTO {
            private Boolean misionAndVision;
            private Boolean productsAndServices;
            private Boolean teamMember;
            private Boolean contactUs;
            private Boolean news;
            private Boolean download;
            private Boolean history;
            private Boolean teamCulture;
            private Boolean performance;
            private Boolean partners;
            private Boolean review;
            private Boolean patent;
            private Boolean footer;
        }

        @Getter
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class WidgetOutDTO {
            private Long widgetId;
            private Integer order;
            private String widgetType;
            private Boolean widgetStatus;
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class KeyVisualAndSloganOutDTO extends WidgetOutDTO {
            private String background;
            private Filter filter;
            private String slogan;
            private String sloganDetail;

            private static KeyVisualAndSloganOutDTO toOutDTO(KeyVisualAndSlogan keyVisualAndSlogan) {
                return KeyVisualAndSloganOutDTO.builder()
                        .widgetId(keyVisualAndSlogan.getId())
                        .order(keyVisualAndSlogan.getOrder())
                        .widgetType(String.valueOf(keyVisualAndSlogan.getWidgetType()))
                        .widgetStatus(keyVisualAndSlogan.getWidgetStatus())
                        .background(keyVisualAndSlogan.getBackground())
                        .filter(keyVisualAndSlogan.getFilter())
                        .slogan(keyVisualAndSlogan.getSlogan())
                        .sloganDetail(keyVisualAndSlogan.getSloganDetail())
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class MissionAndVisionOutDTO extends WidgetOutDTO {
            private String mission;
            private String missionDetail;
            private String vision;
            private String visionDetail;

            private static MissionAndVisionOutDTO toOutDTO(MissionAndVision missionAndVision) {
                return MissionAndVisionOutDTO.builder()
                        .widgetId(missionAndVision.getId())
                        .order(missionAndVision.getOrder())
                        .widgetType(String.valueOf(missionAndVision.getWidgetType()))
                        .widgetStatus(missionAndVision.getWidgetStatus())
                        .mission(missionAndVision.getMission())
                        .missionDetail(missionAndVision.getMissionDetail())
                        .vision(missionAndVision.getVision())
                        .visionDetail(missionAndVision.getVisionDetail())
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class ProductsAndServicesOutDTO extends WidgetOutDTO {
            private List<ProductsAndServicesElementOutDTO> productsAndServicesElements;
            private Boolean callToActionStatus;
            private CallToAction callToAction;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class ProductsAndServicesElementOutDTO {
                private Long productsAndServicesElementId;
                private Long order;
                private String image;
                private String name;
                private String title;
                private String description;

                private static ProductsAndServicesElementOutDTO toOutDTO(ProductsAndServicesElement productsAndServicesElement) {
                    return ProductsAndServicesElementOutDTO.builder()
                            .productsAndServicesElementId(productsAndServicesElement.getId())
                            .order(productsAndServicesElement.getOrder())
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
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class CallToAction {
                private String description;
                private String text;
                private String link;
            }

            private static ProductsAndServicesOutDTO toOutDTO(ProductsAndServices productsAndServices) {
                List<ProductsAndServicesElement> productsAndServicesElements = productsAndServices.getProductsAndServicesElements();
                List<ProductsAndServicesElementOutDTO> productsAndServicesElementOutDTOs = new ArrayList<>();
                for (int i = 0; i < productsAndServicesElements.size(); i++) {
                    for (ProductsAndServicesElement productsAndServicesElement : productsAndServicesElements) {
                        if (productsAndServicesElement.getOrder() != i + 1)
                            continue;
                        productsAndServicesElementOutDTOs.add(ProductsAndServicesElementOutDTO.toOutDTO(productsAndServicesElement));
                    }
                }
                return ProductsAndServicesOutDTO.builder()
                        .widgetId(productsAndServices.getId())
                        .order(productsAndServices.getOrder())
                        .widgetType(String.valueOf(productsAndServices.getWidgetType()))
                        .widgetStatus(productsAndServices.getWidgetStatus())
                        .productsAndServicesElements(productsAndServicesElementOutDTOs)
                        .callToActionStatus(productsAndServices.getCallToActionStatus())
                        .callToAction(productsAndServices.getCallToAction() != null ? CallToAction.builder()
                                .description(productsAndServices.getCallToAction().getDescription())
                                .text(productsAndServices.getCallToAction().getText())
                                .link(productsAndServices.getCallToAction().getLink())
                                .build() : null)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class TeamMemberOutDTO extends WidgetOutDTO {
            private List<TeamMemberElementOutDTO> teamMemberElements;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class TeamMemberElementOutDTO {
                private Long teamMemberElementId;
                private Long order;
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
                @NoArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
                private static class SnsListOutDTO {
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

                    private static SnsListOutDTO toOutDTO(SnsList snsList) {
                        return SnsListOutDTO.builder()
                                .instagramStatus(snsList.getInstagramStatus())
                                .instagram(snsList.getInstagram())
                                .linkedInStatus(snsList.getLinkedInStatus())
                                .linkedIn(snsList.getLinkedIn())
                                .youtubeStatus(snsList.getYoutubeStatus())
                                .youtube(snsList.getYoutube())
                                .notionStatus(snsList.getNotionStatus())
                                .notion(snsList.getNotion())
                                .naverBlogStatus(snsList.getNaverBlogStatus())
                                .naverBlog(snsList.getNaverBlog())
                                .brunchStroyStatus(snsList.getBrunchStroyStatus())
                                .brunchStroy(snsList.getBrunchStroy())
                                .facebookStatus(snsList.getFacebookStatus())
                                .facebook(snsList.getFacebook())
                                .build();
                    }
                }

                private static TeamMemberElementOutDTO toOutDTO(TeamMemberElement teamMemberElement) {
                    return TeamMemberElementOutDTO.builder()
                            .teamMemberElementId(teamMemberElement.getId())
                            .order(teamMemberElement.getOrder())
                            .profile(teamMemberElement.getProfile())
                            .name(teamMemberElement.getName())
                            .group(teamMemberElement.getGroup())
                            .position(teamMemberElement.getPosition())
                            .tagline(teamMemberElement.getTagline())
                            .email(teamMemberElement.getEmail())
                            .snsStatus(teamMemberElement.getSnsStatus())
                            .snsList(teamMemberElement.getSnsList() != null ? SnsListOutDTO.toOutDTO(teamMemberElement.getSnsList()) : null)
                            .build();
                }
            }

            private static TeamMemberOutDTO toOutDTO(TeamMember teamMember) {
                List<TeamMemberElement> teamMemberElements = teamMember.getTeamMemberElements();
                List<TeamMemberElementOutDTO> teamMemberElementOutDTOs = new ArrayList<>();
                for (int i = 0; i < teamMemberElements.size(); i++) {
                    for (TeamMemberElement teamMemberElement : teamMemberElements) {
                        if (teamMemberElement.getOrder() != i + 1)
                            continue;
                        teamMemberElementOutDTOs.add(TeamMemberElementOutDTO.toOutDTO(teamMemberElement));
                    }
                }
                return TeamMemberOutDTO.builder()
                        .widgetId(teamMember.getId())
                        .order(teamMember.getOrder())
                        .widgetType(String.valueOf(teamMember.getWidgetType()))
                        .widgetStatus(teamMember.getWidgetStatus())
                        .teamMemberElements(teamMemberElementOutDTOs)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class ContactUsOutDTO extends WidgetOutDTO {
            private Boolean mapStatus;
            private String fullAddress;
            private String detailedAddress;
            private String latitude; // 위도
            private String longitude; // 경도

            private static ContactUsOutDTO toOutDTO(ContactUs contactUs) {
                return ContactUsOutDTO.builder()
                        .widgetId(contactUs.getId())
                        .order(contactUs.getOrder())
                        .widgetType(String.valueOf(contactUs.getWidgetType()))
                        .widgetStatus(contactUs.getWidgetStatus())
                        .mapStatus(contactUs.getMapStatus())
                        .fullAddress(contactUs.getFullAddress())
                        .detailedAddress(contactUs.getDetailedAddress())
                        .latitude(contactUs.getLatitude())
                        .longitude(contactUs.getLongitude())
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class PerformanceOutDTO extends WidgetOutDTO {
            private List<PerformanceElementOutDTO> performanceElements;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class PerformanceElementOutDTO {
                private Long performanceElementId;
                private Long order;
                private String descrition;
                private String additionalDescrition;
                private String indicator;

                private static PerformanceElementOutDTO toOutDTO(PerformanceElement performanceElement) {
                    return PerformanceElementOutDTO.builder()
                            .performanceElementId(performanceElement.getId())
                            .order(performanceElement.getOrder())
                            .descrition(performanceElement.getDescrition())
                            .additionalDescrition(performanceElement.getAdditionalDescrition())
                            .indicator(performanceElement.getIndicator())
                            .build();
                }
            }

            private static PerformanceOutDTO toOutDTO(Performance performance) {
                List<PerformanceElement> performanceElements = performance.getPerformanceElements();
                List<PerformanceElementOutDTO> performanceElementOutDTOs = new ArrayList<>();
                for (int i = 0; i < performanceElements.size(); i++) {
                    for (PerformanceElement performanceElement : performanceElements) {
                        if (performanceElement.getOrder() != i + 1)
                            continue;
                        performanceElementOutDTOs.add(PerformanceElementOutDTO.toOutDTO(performanceElement));
                    }
                }
                return PerformanceOutDTO.builder()
                        .widgetId(performance.getId())
                        .order(performance.getOrder())
                        .widgetType(String.valueOf(performance.getWidgetType()))
                        .widgetStatus(performance.getWidgetStatus())
                        .performanceElements(performanceElementOutDTOs)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class TeamCultureOutDTO extends WidgetOutDTO {
            private List<TeamCultureElementOutDTO> teamCultureElements;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class TeamCultureElementOutDTO {
                private Long teamCultureElementId;
                private Long order;
                private String image;
                private String culture;
                private String desciption;

                private static TeamCultureElementOutDTO toOutDTO(TeamCultureElement teamCultureElement) {
                    return TeamCultureElementOutDTO.builder()
                            .teamCultureElementId(teamCultureElement.getId())
                            .order(teamCultureElement.getOrder())
                            .image(teamCultureElement.getImage())
                            .culture(teamCultureElement.getCulture())
                            .desciption(teamCultureElement.getDesciption())
                            .build();
                }
            }

            private static TeamCultureOutDTO toOutDTO(TeamCulture teamCulture) {
                List<TeamCultureElement> teamCultureElements = teamCulture.getTeamCultureElements();
                List<TeamCultureElementOutDTO> teamCultureElementOutDTOs = new ArrayList<>();
                for (int i = 0; i < teamCultureElements.size(); i++) {
                    for (TeamCultureElement teamCultureElement : teamCultureElements) {
                        if (teamCultureElement.getOrder() != i + 1)
                            continue;
                        teamCultureElementOutDTOs.add(TeamCultureElementOutDTO.toOutDTO(teamCultureElement));
                    }
                }
                return TeamCultureOutDTO.builder()
                        .widgetId(teamCulture.getId())
                        .order(teamCulture.getOrder())
                        .widgetType(String.valueOf(teamCulture.getWidgetType()))
                        .widgetStatus(teamCulture.getWidgetStatus())
                        .teamCultureElements(teamCultureElementOutDTOs)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class HistoryOutDTO extends WidgetOutDTO {
            private List<HistoryElementOutDTO> historyElements;


            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
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
                            .title(historyElement.getTitle())
                            .description(historyElement.getDescription())
                            .build();
                }
            }

            private static HistoryOutDTO toOutDTO(History history) {
                List<HistoryElement> historyElements = history.getHistoryElements();
                Collections.sort(historyElements, Collections.reverseOrder());
                List<HistoryElementOutDTO> historyElementOutDTOs = new ArrayList<>();
                // check-point 날짜 순으로 출력
                for (HistoryElement historyElement : historyElements) {
                    historyElementOutDTOs.add(HistoryElementOutDTO.toOutDTO(historyElement));
                }
                return HistoryOutDTO.builder()
                        .widgetId(history.getId())
                        .order(history.getOrder())
                        .widgetType(String.valueOf(history.getWidgetType()))
                        .widgetStatus(history.getWidgetStatus())
                        .historyElements(historyElementOutDTOs)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class ReviewOutDTO extends WidgetOutDTO {
            private List<ReviewElementOutDTO> reviewElements;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class ReviewElementOutDTO {
                private Long reviewElementId;
                private Long order;
                private String image;
                private String name;
                private String group;
                private Integer rating;
                private String details;

                private static ReviewElementOutDTO toOutDTO(ReviewElement reviewElement) {
                    return ReviewElementOutDTO.builder()
                            .reviewElementId(reviewElement.getId())
                            .order(reviewElement.getOrder())
                            .image(reviewElement.getImage())
                            .name(reviewElement.getName())
                            .group(reviewElement.getGroup())
                            .rating(reviewElement.getRating())
                            .details(reviewElement.getDetails())
                            .build();
                }
            }

            private static ReviewOutDTO toOutDTO(Review review) {
                List<ReviewElement> reviewElements = review.getReviewElement();
                List<ReviewElementOutDTO> reviewElementOutDTOs = new ArrayList<>();
                for (int i = 0; i < reviewElements.size(); i++) {
                    for (ReviewElement reviewElement : reviewElements) {
                        if (reviewElement.getOrder() != i + 1)
                            continue;
                        reviewElementOutDTOs.add(ReviewElementOutDTO.toOutDTO(reviewElement));
                    }
                }
                return ReviewOutDTO.builder()
                        .widgetId(review.getId())
                        .order(review.getOrder())
                        .widgetType(String.valueOf(review.getWidgetType()))
                        .widgetStatus(review.getWidgetStatus())
                        .reviewElements(reviewElementOutDTOs)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class PatentOutDTO extends WidgetOutDTO {
            private List<PatentElementOutDTO> patentElements;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class PatentElementOutDTO {
                private Long patentElementId;
                private Long order;
                private String patentType;
                private String title;
                private String image;

                private static PatentElementOutDTO toOutDTO(PatentElement patentElement) {
                    return PatentElementOutDTO.builder()
                            .patentElementId(patentElement.getId())
                            .order(patentElement.getOrder())
                            .patentType(String.valueOf(patentElement.getPatentType()))
                            .title(patentElement.getTitle())
                            .image(patentElement.getImage())
                            .build();
                }
            }

            private static PatentOutDTO toOutDTO(Patent patent) {
                List<PatentElement> patentElements = patent.getPatentElements();
                List<PatentElementOutDTO> patentElementOutDTOs = new ArrayList<>();
                for (int i = 0; i < patentElements.size(); i++) {
                    for (PatentElement patentElement : patentElements) {
                        if (patentElement.getOrder() != i + 1)
                            continue;
                        patentElementOutDTOs.add(PatentElementOutDTO.toOutDTO(patentElement));
                    }
                }
                return PatentOutDTO.builder()
                        .widgetId(patent.getId())
                        .order(patent.getOrder())
                        .widgetType(String.valueOf(patent.getWidgetType()))
                        .widgetStatus(patent.getWidgetStatus())
                        .patentElements(patentElementOutDTOs)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class NewsOutDTO extends WidgetOutDTO {
            private List<NewsElementOutDTO> newsElements;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class NewsElementOutDTO {
                private Long newsElementId;
                private Long order;
                private String image;
                private LocalDate date;
                private String press;
                private String title;
                private String description;

                private static NewsElementOutDTO toOutDTO(NewsElement newsElement) {
                    return NewsElementOutDTO.builder()
                            .newsElementId(newsElement.getId())
                            .order(newsElement.getOrder())
                            .image(newsElement.getImage())
                            .date(newsElement.getDate())
                            .press(newsElement.getPress())
                            .title(newsElement.getTitle())
                            .description(newsElement.getDescription())
                            .build();
                }
            }

            private static NewsOutDTO toOutDTO(News news) {
                List<NewsElement> newsElements = news.getNewsElements();
                List<NewsElementOutDTO> newsElementOutDTOs = new ArrayList<>();
                for (int i = 0; i < newsElements.size(); i++) {
                    for (NewsElement newsElement : newsElements) {
                        if (newsElement.getOrder() != i + 1)
                            continue;
                        newsElementOutDTOs.add(NewsElementOutDTO.toOutDTO(newsElement));
                    }
                }
                return NewsOutDTO.builder()
                        .widgetId(news.getId())
                        .order(news.getOrder())
                        .widgetType(String.valueOf(news.getWidgetType()))
                        .widgetStatus(news.getWidgetStatus())
                        .newsElements(newsElementOutDTOs)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class DownloadOutDTO extends WidgetOutDTO {
            private String description;
            private String mediaKitFile;
            private String introFile;

            private static DownloadOutDTO toOutDTO(Download download) {
                return DownloadOutDTO.builder()
                        .widgetId(download.getId())
                        .order(download.getOrder())
                        .widgetType(String.valueOf(download.getWidgetType()))
                        .widgetStatus(download.getWidgetStatus())
                        .description(download.getDescription())
                        .mediaKitFile(download.getMediaKitFile())
                        .introFile(download.getIntroFile())
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class PartnersOutDTO extends WidgetOutDTO {
            private List<PartnersElementOutDTO> partnersElements;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class PartnersElementOutDTO {
                private Long partnersElementId;
                private Long order;
                private String partnersType;
                private String companyName;
                private String logo;

                private static PartnersElementOutDTO toOutDTO(PartnersElement partnersElement) {
                    return PartnersElementOutDTO.builder()
                            .partnersElementId(partnersElement.getId())
                            .order(partnersElement.getOrder())
                            .partnersType(String.valueOf(partnersElement.getPartnersType()))
                            .companyName(partnersElement.getCompanyName())
                            .logo(partnersElement.getLogo())
                            .build();
                }
            }

            private static PartnersOutDTO toOutDTO(Partners partners) {
                List<PartnersElement> partnersElements = partners.getPartnersElements();
                List<PartnersElementOutDTO> partnersElementOutDTOs = new ArrayList<>();
                for (int i = 0; i < partnersElements.size(); i++) {
                    for (PartnersElement partnersElement : partnersElements) {
                        if (partnersElement.getOrder() != i + 1)
                            continue;
                        partnersElementOutDTOs.add(PartnersElementOutDTO.toOutDTO(partnersElement));
                    }
                }
                return PartnersOutDTO.builder()
                        .widgetId(partners.getId())
                        .order(partners.getOrder())
                        .widgetType(String.valueOf(partners.getWidgetType()))
                        .widgetStatus(partners.getWidgetStatus())
                        .partnersElements(partnersElementOutDTOs)
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class ChannelOutDTO extends WidgetOutDTO {
            private SnsList snsList;

            @Getter
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            private static class SnsList {
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

            private static ChannelOutDTO toOutDTO(Channel channel) {
                return ChannelOutDTO.builder()
                        .widgetId(channel.getId())
                        .widgetType(String.valueOf(channel.getWidgetType()))
                        .widgetStatus(channel.getWidgetStatus())
                        .snsList(channel.getSnsList() != null ? SnsList.builder()
                                .instagramStatus(channel.getSnsList().getInstagramStatus())
                                .instagram(channel.getSnsList().getInstagram())
                                .linkedInStatus(channel.getSnsList().getLinkedInStatus())
                                .linkedIn(channel.getSnsList().getLinkedIn())
                                .youtubeStatus(channel.getSnsList().getYoutubeStatus())
                                .youtube(channel.getSnsList().getYoutube())
                                .notionStatus(channel.getSnsList().getNotionStatus())
                                .notion(channel.getSnsList().getNotion())
                                .naverBlogStatus(channel.getSnsList().getNaverBlogStatus())
                                .naverBlog(channel.getSnsList().getNaverBlog())
                                .brunchStroyStatus(channel.getSnsList().getBrunchStroyStatus())
                                .brunchStroy(channel.getSnsList().getBrunchStroy())
                                .facebookStatus(channel.getSnsList().getFacebookStatus())
                                .facebook(channel.getSnsList().getFacebook())
                                .build() : null)
                        .build();
            }
        }

        public static IntroPageOutDTO toOutDTO(IntroPage introPage, List<Integer> orderList) {
            List<Widget> widgets = introPage.getWidgets();
            List<WidgetOutDTO> widgetOutDTOs = new ArrayList<>();
            // check-point 바뀐 순서대로 보내줄지, 아니면 그냥 고정된 순서로 보내고 orderList로 처리할지
            for (int i = 0; i < orderList.size(); i++) {
                for (Widget widget : widgets) {
                    if (widget.getOrder() != i + 1)
                        continue;
                    if (widget instanceof KeyVisualAndSlogan)
                        widgetOutDTOs.add(KeyVisualAndSloganOutDTO.toOutDTO((KeyVisualAndSlogan) widget));
                    else if (widget instanceof MissionAndVision)
                        widgetOutDTOs.add(MissionAndVisionOutDTO.toOutDTO((MissionAndVision) widget));
                    else if (widget instanceof ProductsAndServices)
                        widgetOutDTOs.add(ProductsAndServicesOutDTO.toOutDTO((ProductsAndServices) widget));
                    else if (widget instanceof TeamMember)
                        widgetOutDTOs.add(TeamMemberOutDTO.toOutDTO((TeamMember) widget));
                    else if (widget instanceof ContactUs)
                        widgetOutDTOs.add(ContactUsOutDTO.toOutDTO((ContactUs) widget));
                    else if (widget instanceof Performance)
                        widgetOutDTOs.add(PerformanceOutDTO.toOutDTO((Performance) widget));
                    else if (widget instanceof TeamCulture)
                        widgetOutDTOs.add(TeamCultureOutDTO.toOutDTO((TeamCulture) widget));
                    else if (widget instanceof History)
                        widgetOutDTOs.add(HistoryOutDTO.toOutDTO((History) widget));
                    else if (widget instanceof Review)
                        widgetOutDTOs.add(ReviewOutDTO.toOutDTO((Review) widget));
                    else if (widget instanceof Patent)
                        widgetOutDTOs.add(PatentOutDTO.toOutDTO((Patent) widget));
                    else if (widget instanceof News)
                        widgetOutDTOs.add(NewsOutDTO.toOutDTO((News) widget));
                    else if (widget instanceof Download)
                        widgetOutDTOs.add(DownloadOutDTO.toOutDTO((Download) widget));
                    else if (widget instanceof Partners)
                        widgetOutDTOs.add(PartnersOutDTO.toOutDTO((Partners) widget));
                    else if (widget instanceof Channel)
                        widgetOutDTOs.add(ChannelOutDTO.toOutDTO((Channel) widget));
                }
            }
            return IntroPageOutDTO.builder()
                    .introPageId(introPage.getId())
                    .introPageStatus(introPage.getIntroPageStatus())
                    .theme(ThemeOutDTO.builder()
                            .type(introPage.getTheme().getType())
                            .color(introPage.getTheme().getColor())
                            .build())
                    .companyInfo(CompanyInfoOutDTO.builder()
                            .companyName(introPage.getCompanyInfo().getCompanyName())
                            .startDate(introPage.getCompanyInfo().getStartDate())
                            .representative(introPage.getCompanyInfo().getRepresentative())
                            .logo(introPage.getCompanyInfo().getLogo())
                            .contactEmail(introPage.getCompanyInfo().getContactEmail())
                            .bizNum(introPage.getCompanyInfo().getBizNum())
                            .phoneNumber(introPage.getCompanyInfo().getPhoneNumber())
                            .faxNumber(introPage.getCompanyInfo().getFaxNumber())
                            .build())
                    .siteInfo(SiteInfoOutDTO.builder()
                            .pavicon(introPage.getSiteInfo().getPavicon())
                            .subDomain(introPage.getSiteInfo().getSubDomain())
                            .title(introPage.getSiteInfo().getTitle())
                            .description(introPage.getSiteInfo().getDescription())
                            .build())
                    .headerAndFooter(HeaderAndFooterOutDTO.builder()
                            .misionAndVision(introPage.getHeaderAndFooter().getMisionAndVision())
                            .productsAndServices(introPage.getHeaderAndFooter().getProductsAndServices())
                            .teamMember(introPage.getHeaderAndFooter().getTeamMember())
                            .contactUs(introPage.getHeaderAndFooter().getContactUs())
                            .news(introPage.getHeaderAndFooter().getNews())
                            .download(introPage.getHeaderAndFooter().getDownload())
                            .history(introPage.getHeaderAndFooter().getHistory())
                            .teamCulture(introPage.getHeaderAndFooter().getTeamCulture())
                            .performance(introPage.getHeaderAndFooter().getPerformance())
                            .partners(introPage.getHeaderAndFooter().getPartners())
                            .review(introPage.getHeaderAndFooter().getReview())
                            .patent(introPage.getHeaderAndFooter().getPatent())
                            .footer(introPage.getHeaderAndFooter().getFooter())
                            .build())
                    .widgets(widgetOutDTOs)
                    .build();
        }
    }
}
