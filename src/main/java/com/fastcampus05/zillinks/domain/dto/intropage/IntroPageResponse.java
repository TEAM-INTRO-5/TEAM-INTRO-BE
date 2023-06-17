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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
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
                else if (widget instanceof TeamMember) {
                }
                else if (widget instanceof ContactUs) {
                }
                else if (widget instanceof Performance) {
                }
                else if (widget instanceof TeamCulture) {
                }
                else if (widget instanceof History) {
                }
                else if (widget instanceof Review) {
                }
                else if (widget instanceof Patent) {
                }
                else if (widget instanceof News) {
                }
                else if (widget instanceof Download) {
                }
                else if (widget instanceof Partners) {
                }
                else if (widget instanceof Channel) {
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
                    .widgets(widgetOutDTOs)
                    .build();
        }
    }
}
