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
import java.util.ArrayList;
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
                ProductsAndServices productsAndServices,
                List<Long> orderList
        ) {
            List<ProductsAndServicesElement> productsAndServicesElements = productsAndServices.getProductsAndServicesElements();
            List<ProductsAndServicesElementOutDTO> productsAndServicesElementOutDTOs = new ArrayList<>();
            for (int i = 0; i < orderList.size(); i++) {
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

}
