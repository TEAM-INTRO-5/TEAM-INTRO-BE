package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class IntroPageRequest {

    @Getter
    public static class UpdateInDTO {

        private String color;

//         check-point, widget 관련 정리 후 추가
//         private List<Widget> widgets;
    }

    @Getter
    public static class UpdateInfoInDTO {

        private String pavicon; // 경로
        @JsonProperty("web_page_name")
        private String webPageName;
        @JsonProperty("sub_domain")
        private String subDomain;
        private String title;
        private String description;
    }

}
