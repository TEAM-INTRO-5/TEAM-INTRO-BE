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

import java.util.ArrayList;
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
            // check-point 단일 객체 요소 추가후 코드 수정
            private List<ProductsAndServicesElement> productsAndServicesElements;
            private Boolean callToActionStatus;
            private CallToAction callToAction;

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
                return ProductsAndServicesOutDTO.builder()
                        .widgetId(productsAndServices.getId())
                        .widgetStatus(productsAndServices.getWidgetStatus())
//                        .productsAndServicesElements()
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
            // check-point 단일 객체 요소 추가후 코드 수정
//            private List<TeamMemberElement> teamMemberElements;

            private static TeamMemberOutDTO toOutDTO(TeamMember teamMember) {
                return TeamMemberOutDTO.builder()
                        .widgetId(teamMember.getId())
                        .widgetStatus(teamMember.getWidgetStatus())
//                        .teamMemberElements()
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
            // check-point 단일 객체 요소 추가후 코드 수정
//            private List<PerformanceElement> performanceElements;

            private static PerformanceOutDTO toOutDTO(Performance performance) {
                return PerformanceOutDTO.builder()
                        .widgetId(performance.getId())
                        .widgetStatus(performance.getWidgetStatus())
//                        .performanceElements()
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class TeamCultureOutDTO extends WidgetOutDTO {
            // check-point 단일 객체 요소 추가후 코드 수정
//            private List<TeamCultureElement> teamCultureElements;

            private static TeamCultureOutDTO toOutDTO(TeamCulture teamCulture) {
                return TeamCultureOutDTO.builder()
                        .widgetId(teamCulture.getId())
                        .widgetStatus(teamCulture.getWidgetStatus())
//                        .teamCultureElements()
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class HistoryOutDTO extends WidgetOutDTO {
            // check-point 단일 객체 요소 추가후 코드 수정
//            private List<HistoryElement> historyElements;

            private static HistoryOutDTO toOutDTO(History history) {
                return HistoryOutDTO.builder()
                        .widgetId(history.getId())
                        .widgetStatus(history.getWidgetStatus())
//                        .historyElements()
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class ReviewOutDTO extends WidgetOutDTO {
            // check-point 단일 객체 요소 추가후 코드 수정
//            private List<ReviewElement> reviewElements;

            private static ReviewOutDTO toOutDTO(Review review) {
                return ReviewOutDTO.builder()
                        .widgetId(review.getId())
                        .widgetStatus(review.getWidgetStatus())
//                        .reviewElements()
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class PatentOutDTO extends WidgetOutDTO {
            // check-point 단일 객체 요소 추가후 코드 수정
//            private List<PatentElement> patentElements;

            private static PatentOutDTO toOutDTO(Patent patent) {
                return PatentOutDTO.builder()
                        .widgetId(patent.getId())
                        .widgetStatus(patent.getWidgetStatus())
//                        .patentElements()
                        .build();
            }
        }

        @Getter
        @SuperBuilder
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        private static class NewsOutDTO extends WidgetOutDTO {
            // check-point 단일 객체 요소 추가후 코드 수정
//            private List<NewsElement> newsElements;

            private static NewsOutDTO toOutDTO(News news) {
                return NewsOutDTO.builder()
                        .widgetId(news.getId())
                        .widgetStatus(news.getWidgetStatus())
//                        .newsElements()
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
            // check-point 단일 객체 요소 추가후 코드 수정
//            private List<PartnersElement> partnersElements;

            private static PartnersOutDTO toOutDTO(Partners partners) {
                return PartnersOutDTO.builder()
                        .widgetId(partners.getId())
                        .widgetStatus(partners.getWidgetStatus())
//                        .partnersElements()
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
                private String instagram;
                private String linkedIn;
                private String youtube;
                private String notion;
                private String naverBlog;
                private String brunchStroy;
                private String facebook;
            }

            private static ChannelOutDTO toOutDTO(Channel channel) {
                return ChannelOutDTO.builder()
                        .widgetId(channel.getId())
                        .widgetStatus(channel.getWidgetStatus())
                        .snsList(channel.getSnsList() != null ? SnsList.builder()
                                .instagram(channel.getSnsList().getInstagram())
                                .linkedIn(channel.getSnsList().getLinkedIn())
                                .youtube(channel.getSnsList().getYoutube())
                                .notion(channel.getSnsList().getNotion())
                                .naverBlog(channel.getSnsList().getNaverBlog())
                                .brunchStroy(channel.getSnsList().getBrunchStroy())
                                .facebook(channel.getSnsList().getFacebook())
                                .build() : null)
                        .build();
            }
        }

        public static IntroPageOutDTO toOutDTO(IntroPage introPage) {
            List<Widget> widgets = introPage.getWidgets();
            List<WidgetOutDTO> widgetOutDTOs = new ArrayList<>();
            for (Widget widget : widgets) {
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
