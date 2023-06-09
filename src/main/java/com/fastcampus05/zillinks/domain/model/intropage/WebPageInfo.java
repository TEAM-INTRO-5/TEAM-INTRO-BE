package com.fastcampus05.zillinks.domain.model.intropage;

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
}
