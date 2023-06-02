package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.SaveStatus;
import com.fastcampus05.zillinks.domain.model.intropage.ZillinksData;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

public class IntroPageRequest {

    @Getter
    public static class SaveInDTO {

        private String pavicon; // 경로

        @JsonProperty("web_page_name")
        private String webPageName;

        @JsonProperty("sub_domain")
        private String subDomain;

        private String title;

        private String description;

        private String logo;

        @JsonProperty("intro_file")
        private String introFile;

        @JsonProperty("media_kit_file")
        private String mediaKitFile;

        private SaveStatus status;

        public IntroPage toEntity(User user, ZillinksData zillinksData, String trackingCode) {
            return IntroPage.builder()
                    .pavicon(pavicon)
                    .webPageName(webPageName)
                    .subDomain(subDomain)
                    .title(title)
                    .description(description)
                    .zillinksData(zillinksData)
                    .logo(logo)
                    .introFile(introFile)
                    .mediaKitFile(mediaKitFile)
                    .trackingCode(trackingCode)
                    .status(status)
                    .user(user)
                    .build();
        }
    }

    @Getter
    public static class UpdateInDTO {

        private String name;

        @JsonProperty("biz_num")
        private String bizNum;

        @JsonProperty("contact_email")
        private String contactEmail;

        private String tagline;

        private String logo;

        @JsonProperty("intro_file")
        private String introFile;

        @JsonProperty("media_kit_file")
        private String mediaKitFile;
    }

}