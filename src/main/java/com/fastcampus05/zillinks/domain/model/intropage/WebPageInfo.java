package com.fastcampus05.zillinks.domain.model.intropage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WebPageInfo {
    private String pavicon; // 경로

    @NotEmpty
    private String webPageName;

    @NotEmpty
    private String subDomain;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    public WebPageInfo(WebPageInfo webPageInfo) {
        this.pavicon = webPageInfo.getPavicon();
        this.webPageName = webPageInfo.getWebPageName();
        this.subDomain = webPageInfo.getSubDomain();
        this.title = webPageInfo.getTitle();
        this.description = webPageInfo.getDescription();
    }
}
