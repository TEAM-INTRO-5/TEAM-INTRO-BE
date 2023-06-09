package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fastcampus05.zillinks.domain.model.intropage.SaveStatus;
import com.fastcampus05.zillinks.domain.model.intropage.WebPageInfo;
import com.fastcampus05.zillinks.domain.model.intropage.ZillinksData;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class IntroPageRequest {

    @Getter
    public static class UpdateInDTO {

        @JsonProperty("intro_page_id")
        private Long id;

        private String color;

//         check-point, widget 관련 정리 후 추가
//         private List<Widget> widgets;
    }

    @Getter
    public static class UpdateInfoInDTO {

        @JsonProperty("intro_page_id")
        private Long id;

        @JsonProperty("web_page_info")
        private WebPageInfo webPageInfo;
    }

}
