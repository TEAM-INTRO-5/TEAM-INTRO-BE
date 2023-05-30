package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.ZillinkData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class IntroPageResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SaveOutDTO {
        private Long id;
        private ZillinkData zillinkData;
        private String logo;
        private String introFile;
        private String mediaKitFile;
        private String trackingCode;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class FindOutDTO {
        private Long id;
        private ZillinkData zillinkData;
        private String logo;
        private String introFile;
        private String mediaKitFile;
        private String trackingCode;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UpdateOutDTO {
        private Long id;
        private ZillinkData zillinkData;
        private String logo;
        private String introFile;
        private String mediaKitFile;
        private String trackingCode;
    }
}
