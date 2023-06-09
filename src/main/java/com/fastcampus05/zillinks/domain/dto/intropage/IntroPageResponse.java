package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.WebPageInfo;
import com.fastcampus05.zillinks.domain.model.intropage.ZillinksData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class IntroPageResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class FindOutDTO {
        private Long id;
        private String color;
        private WebPageInfo webPageInfo;
        private String trackingCode;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SaveOutDTO {
        private Long id;
        private String pavicon;
        @JsonProperty("web_page_name")
        private String webPageName;
        @JsonProperty("sub_domain")
        private String subDomain;
        private String title;
        private String description;
        @JsonProperty("zillinks_data")
        private ZillinksData zillinksData;
        private String logo;
        @JsonProperty("intro_file")
        private String introFile;
        @JsonProperty("media_kit_file")
        private String mediaKitFile;
        @JsonProperty("tracking_code")
        private String trackingCode;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UpdateOutDTO {
        private Long id;
        private ZillinksData zillinksData;
        private String logo;
        private String introFile;
        private String mediaKitFile;
        private String trackingCode;
    }
}
